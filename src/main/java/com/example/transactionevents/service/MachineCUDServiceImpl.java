package com.example.transactionevents.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.transactionevents.repository.MachineRepository;
import com.example.transactionevents.repository.model.Machine;
import com.example.transactionevents.repository.model.MachineOutbox;
import com.example.transactionevents.repository.model.MachineOutbox.OperationType;
import com.example.transactionevents.repository.model.MachineOutbox.TransferState;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation for <code>MachineCUDService</code>.
 */
@Service
@Slf4j
public class MachineCUDServiceImpl implements MachineCUDService {

    @Autowired
    private MachineRepository machineRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    @Override
    public Machine createMachine(Machine machine) {
        machine.setId(UUID.randomUUID());

        final Instant now = Instant.now();
        machine.setCreatedAt(now);
        machine.setUpdatedAt(now);

        final Machine createdMachine = machineRepo.insert(machine);
        final MachineOutbox outbox = createOutboxDocument(OperationType.CREATED, createdMachine);
        mongoTemplate.insert(outbox);
        log.debug("Created new machine: {}", createdMachine);
        return createdMachine;
    }

    @Transactional
    @Override
    public Machine updateMachine(Machine machine, Machine oldMachine) {
        machine.setUpdatedAt(Instant.now());

        final Machine updatedMachine = machineRepo.save(machine);
        final MachineOutbox outbox = createOutboxDocument(OperationType.UPDATED, updatedMachine);
        outbox.setOldMachine(oldMachine);
        mongoTemplate.insert(outbox);
        log.debug("Updated machine: {}", updatedMachine);
        return updatedMachine;
    }

    @Transactional
    @Override
    public void deleteMachine(Machine machine) {
        final Instant now = Instant.now();
        machine.setUpdatedAt(now);
        machine.setDeletedAt(now);

        final Machine deletedMachine = machineRepo.save(machine);
        final MachineOutbox outbox = createOutboxDocument(OperationType.DELETED, deletedMachine);
        mongoTemplate.insert(outbox);
        log.debug("Deleted machine: {}", deletedMachine);
    }

    private MachineOutbox createOutboxDocument(OperationType opType, Machine ref) {
        MachineOutbox result = new MachineOutbox();
        result.setOperationType(opType);
        result.setTransferItem(ref);
        result.setState(TransferState.PENDING);
        return result;
    }

}

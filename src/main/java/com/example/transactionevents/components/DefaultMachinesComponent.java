package com.example.transactionevents.components;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.transactionevents.repository.MachineRepository;
import com.example.transactionevents.repository.model.Machine;
import com.example.transactionevents.repository.model.MachineOutbox;
import com.example.transactionevents.repository.model.MachineOutbox.OperationType;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Initialize dummy common machine data by startup.
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DefaultMachinesComponent {

    @Autowired
    private final @NonNull MachineRepository machineRepository;

    @Autowired
    private final @NonNull MongoTemplate mongoTemplate;

    /**
     * Initializes default machines.
     */
    public void createDefaultMachines() {
        createCommonMachine("111");
    }

    @Transactional
    private void deleteMachine(Machine machine) {
        machine.setDeletedAt(Instant.now());
        machine.setDeletedBy("system-delete");
        final Machine deletedMachine = machineRepository.save(machine);
        final MachineOutbox outbox = createOutboxDocument(OperationType.DELETED, deletedMachine);
        mongoTemplate.insert(outbox);
    }

    @Transactional
    private void updateMachine(Machine machine) {
        final Machine changedMachine = machineRepository.save(machine);
        final MachineOutbox outbox = createOutboxDocument(OperationType.UPDATED, changedMachine);
        mongoTemplate.insert(outbox);
    }

    @Transactional
    private void createCommonMachine(String organizationId) {
        Machine machine = new Machine();
        machine.setId(UUID.randomUUID());
        machine.setOrganizationId(organizationId);
        machine.setName("Test Maschine " + organizationId);
        machine.setSerial("1234");
        machine.setMake("VW");
        machine.setModel("Beetle");
        machine.setSapMachineType("1");
        machine.setSapMachineSubtype("2");
        machine.setCreatedBy("system");
        machine.setUpdatedBy("system");
        final Instant now = Instant.now();
        machine.setCreatedAt(now);
        machine.setUpdatedAt(now);

        // Apply characteristics on common machine
        final Machine createdMachine = machineRepository.insert(machine);
        final MachineOutbox outbox = createOutboxDocument(MachineOutbox.OperationType.CREATED, createdMachine);
        mongoTemplate.insert(outbox);
    }

    private MachineOutbox createOutboxDocument(MachineOutbox.OperationType opType, Machine ref) {
        MachineOutbox result = new MachineOutbox();
        result.setOperationType(opType);
        result.setTransferItem(ref);
        result.setState(MachineOutbox.TransferState.PENDING);
        return result;
    }
}

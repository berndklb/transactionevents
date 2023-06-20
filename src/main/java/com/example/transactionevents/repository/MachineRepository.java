package com.example.transactionevents.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.transactionevents.repository.model.Machine;

/**
 * The Interface MachineRepository.
 */
public interface MachineRepository extends MongoRepository<Machine, UUID> {


}

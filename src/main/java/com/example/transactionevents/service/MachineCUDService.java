package com.example.transactionevents.service;

import com.example.transactionevents.repository.model.Machine;

/**
 * The Interface MachineCUDService.
 */
public interface MachineCUDService {

    /**
     * Creates the machine.
     * 
     * @param machine the machine to create
     * @return the created machine
     */
    Machine createMachine(Machine machine);

    /**
     * Update machine.
     * 
     * @param id the id
     * @param newMachine the updated machine data
     * @param oldMachine the machine data before the update
     * @return the updated machine
     */
    Machine updateMachine(Machine newMachine, Machine oldMachine);

    /**
     * Delete machine.
     * 
     * @param machine the machine to delete
     */
    void deleteMachine(Machine machine);

}

package com.example.transactionevents.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MachineInitializer {

    @Autowired
    private DefaultMachinesComponent defaultMachinesComponent;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeMachineSyncProcess() {
        log.info("Start init machines...");
        defaultMachinesComponent.createDefaultMachines();
        log.info("(1/2): Default machines initialized.");
    }

}

package com.example.transactionevents.components;

import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.transactionevents.repository.model.MachineOutbox;

import lombok.extern.slf4j.Slf4j;

/**
 * The OutboxTransactionTrigger triggers the outbox after saving documents to send items to message
 * bus.<br/>
 * It is used to implement the 'Transactional outbox pattern.'
 */
@Slf4j
// @Async
@Component
public class OutboxTransactionTrigger {

    /** The outbox sender. */
    // @Autowired
    // private OutboxSender outboxSender;

    /**
     * Handle transaction event after commit on mongodb.
     * 
     * @param event the event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleTransactionEvent(AfterSaveEvent<MachineOutbox> event) {
        System.out.println("Computing AfterSaveEvent .......");
        // if (MachineOutbox.COLLECTION.equals(event.getCollectionName())) {
        // log.info("Starting sending outbox items after spring mongo-db trx commit...");
        // outboxSender.sendItemsToMessageBus();
        // }
    }
}

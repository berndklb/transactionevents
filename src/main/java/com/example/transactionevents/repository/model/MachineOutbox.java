package com.example.transactionevents.repository.model;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * The Outbox holds items to send via kafka.<br/>
 * It is used to send items to message bus via 'Transactional outbox pattern.'<br/>
 * Add MachineOutbox items with <code>mongoTemplate.insert(outbox);</code><br/>
 * It is important to use the annotation <code>@Transactional</code> on the insert/update/delete
 * service method!
 */
@Document(MachineOutbox.COLLECTION)
@Getter
@Setter
public class MachineOutbox {

    /** The collection name in lowerCamelCase. */
    public static final String COLLECTION = "machineOutbox";

    /** The enum TransferState indicates if the outbox item was transferred. */
    public enum TransferState {

        /** Outbox item is pending. */
        PENDING,

        /** Outbox item was transferred. */
        TRANSFERRED
    }

    /**
     * The enum OperationType indicates the operation occured on outbox item.
     */
    public enum OperationType {

        /** The item was created. */
        CREATED,

        /** The item was updated. */
        UPDATED,

        /** The item was deleted. */
        DELETED
    }

    /** The identifier. DO NOT SET MANUALLY, this is auto filled by mongodb at creation time. */
    @Id
    private String id;

    /** The identifier. */
    private OperationType operationType;

    /** The item to send. */
    private Machine transferItem;

    /** The old machine data (e.g. before the update, otherwise null) */
    private Machine oldMachine;

    /** The transfer state. */
    private TransferState state;

    /** The instant created at which is auto filled by mongodb at creation time. DO NOT SET MANUALLY! */
    @CreatedDate
    private Instant createdAt;

    /** The instant transferred at. */
    private Instant transferredAt;

}

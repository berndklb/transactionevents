package com.example.transactionevents.repository.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Document(Machine.COLLECTION)
@Getter
@Setter
public class Machine {

    /** The collection name in lowerCamelCase. */
    public static final String COLLECTION = "machine";

    /** The unique identifier. */
    @Id
    private UUID id;

    /** The unique identifier of the dependent organization. */
    @NotBlank(message = "Organization id must be filled.")
    private String organizationId;

    /** The name. */
    @NotBlank(message = "Name must not be blank")
    private String name;

    /** The serial. */
    private String serial;

    /** The make. */
    @NotBlank(message = "Make must not be blank")
    private String make;

    /** The sap machine type. */
    private String sapMachineType;

    /** The sap machine subtype. */
    private String sapMachineSubtype;

    /** The warranty start date. */
    private LocalDate warrantyStart;

    /** The warranty end date. */
    private LocalDate warrantyEnd;

    /** The model. */
    @NotBlank(message = "Model must not be blank")
    private String model;

    /** Flag if machine is archived. Archived machines does not receive machine data but is in UI. */
    @NotNull
    private Boolean archived = Boolean.FALSE;

    /** The flag if the machine is hidden. */
    private Boolean hidden = Boolean.FALSE;

    /** The created by. */
    @NotNull
    private String createdBy;

    /** The created at. */
    @NotNull
    private Instant createdAt;

    /** The updated by. */
    @NotNull
    private String updatedBy;

    /** The updated at. */
    @NotNull
    private Instant updatedAt;

    /** The deleted by. */
    private String deletedBy;

    /** The deleted at. */
    private Instant deletedAt;
}
package org.vaadin.example.backend.dto;

import lombok.Value;

@Value
public class ErrorDTO {
    Integer numInsured;
    ConceptContribution concept;
    String message;
}

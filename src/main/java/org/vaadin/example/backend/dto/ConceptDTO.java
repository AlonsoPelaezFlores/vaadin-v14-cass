package org.vaadin.example.backend.dto;

import lombok.Value;

@Value
public class ConceptDTO{
        String concept;
        Double amount;
        Integer hours;
        Integer days;
        Integer percentageCASS;
        Integer contribution;
        Integer percentageIRPF;
        Integer withHolding;
}

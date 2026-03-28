package org.vaadin.example.backend.dto;

import lombok.Value;

@Value
public class InsuredDTO{
        Integer ordre;
        String number;
        String name;
        Integer percentage;
        Double totalCASS;
        Double contributionCASS;
        Double totalIRPF;
        Double irpfWithHolding;
}

package org.vaadin.example.backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AddConceptForm{
        private ConceptContribution concept;
        private String amount;
        private Integer hours;
        private Integer days;
}

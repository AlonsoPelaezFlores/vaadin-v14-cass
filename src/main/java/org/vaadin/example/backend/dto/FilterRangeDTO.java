package org.vaadin.example.backend.dto;

import lombok.Value;

@Value
public class FilterRangeDTO{
        Integer monthSince;
        Integer yearSince;
        Integer monthUntil;
        Integer yearUntil;
}

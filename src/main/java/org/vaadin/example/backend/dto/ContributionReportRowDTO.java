package org.vaadin.example.backend.dto;


import lombok.Value;

import java.time.LocalDate;
import java.time.YearMonth;
@Value
public class ContributionReportRowDTO{
        YearMonth period;
        String sheetNumber;
        String irpfNumber;
        LocalDate submissionDate;
        Double total;
        Double payment;
        String status;

}

package org.vaadin.example.backend.dto;


import lombok.Getter;

@Getter
public class FilerData {
    String filerNumber;
    String filerName;
    String nrtNumber;
    String irpfNumber;
    Double contributionBalance;

    public FilerData() {
        this.filerNumber = "123456";
        this.filerName = "Josep Garcia Garcia";
        this.nrtNumber = "F-123456";
        this.irpfNumber = "Example IRPF";
        this.contributionBalance = 0.0;
    }
}

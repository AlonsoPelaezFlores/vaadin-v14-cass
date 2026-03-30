package org.vaadin.example.backend.dto;

public enum ConceptContribution {
    SBA("Salari Base"),
    ARV("Altres retribucions variables"),
    RES("Rendes en especié"),
    CPP("Contribuccions a plans de pensions"),
    PRE("Preavís"),
    CEC("Compensació econònomica prevista pel Codi Relacions Laborals Art.90"),
    CEI("Excés de compensació econòmica, indemnització i d'altres similars"),

    SBD("Salari Art.18 i 222"),
    AFI("Afiliacions compte propi i règims especials"),
    ARE("Altres rendes en règim especials");
    String description;
    ConceptContribution(String description){
        this.description = description;
    }
}

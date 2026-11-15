package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.entity;

public enum ApplicationStatus {
    APPLIED("Aplicação"), // Aplicada
    PENDING("Pendente"), // Pendente
    LATE("Atrasada");     // Atrasada

    String name;

    ApplicationStatus(String atrasada) {
    }
}

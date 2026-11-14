package com.vetcare.petmeds.modules.vaccinationRecord.entity;

public enum ApplicationStatus {
    APPLIED("Aplicação"), // Aplicada
    PENDING("Pendente"), // Pendente
    LATE("Atrasada");     // Atrasada

    String name;

    ApplicationStatus(String atrasada) {
    }
}

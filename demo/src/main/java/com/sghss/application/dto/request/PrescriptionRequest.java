package com.sghss.application.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class PrescriptionRequest {
    private String notes;
    private List<MedicationRequest> medications;

    @Data
    public static class MedicationRequest {
        private String name;
        private String dosage;
        private String instructions;
    }
}

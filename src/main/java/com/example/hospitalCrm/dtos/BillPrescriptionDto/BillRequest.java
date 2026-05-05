package com.example.hospitalCrm.dtos.BillPrescriptionDto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {

    private Long appointmentId;

    private Long prescriptionId;

    private Long docId;

}

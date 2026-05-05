package com.example.hospitalCrm.dtos.BillPrescriptionDto;


import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemBillResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {

    private Long appointmentId;

    private Long prescriptionId;

    private List<PrescriptionItemBillResponse> items;

    private Long netTotal;

    private Long discount;

    private Long total;
}

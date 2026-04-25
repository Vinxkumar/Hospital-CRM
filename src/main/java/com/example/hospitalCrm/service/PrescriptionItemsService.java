package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsRequest;
import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsResponse;

public interface PrescriptionItemsService {

    PrescriptionItemsResponse createPrescriptionItem(Long prescriptionId, PrescriptionItemsRequest request);

    PrescriptionItemsResponse deletePrescriptionItem(Long prescriptionId, Long prescriptionItemsId);

}

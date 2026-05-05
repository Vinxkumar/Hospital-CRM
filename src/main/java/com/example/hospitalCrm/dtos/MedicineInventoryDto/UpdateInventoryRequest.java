package com.example.hospitalCrm.dtos.MedicineInventoryDto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInventoryRequest {

    private Long inStock;

    private String batchNo;

    private LocalDateTime manufacturedAt;

    private LocalDateTime expireAt;

    private Long perPrice;

}

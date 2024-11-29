package com.example.stankingibdd.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель связи водительского удостоверения и категории")
public class DrivingLicenseCategoryLinkDto {

    @Schema(description = "Название категории")
    private CategoryType categoryName;

    @Schema(description = "Номер водительского удостоверения")
    private String licenseNumber;

    @Schema(description = "Телефон клиента. Служит как username")
    private String phone;
}

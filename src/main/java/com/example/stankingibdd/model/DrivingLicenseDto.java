package com.example.stankingibdd.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель водительского удостоверения")
public class DrivingLicenseDto {

    @Schema(description = "Номер водительского удостоверения")
    private String licenseNumber;

    @Schema(description = "Дата получения водительского удостоверения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;

    @Schema(description = "Дата истечения срока действия водительского удостоверения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @Schema(description = "Код подразделения водительского удостоверения")
    private String departmentCode;

    @Schema(description = "Телефон клиента. Служит как username")
    private String phone;
}

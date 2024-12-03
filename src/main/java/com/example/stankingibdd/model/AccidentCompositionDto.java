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
@Schema(description = "Модель связи аварии и транспортного средства")
public class AccidentCompositionDto {

    @Schema(description = "Идентификатор аварии")
    private String accidentId;

    @Schema(description = "Регистрационный номер транспортного средства")
    private String registrationNumber;
}

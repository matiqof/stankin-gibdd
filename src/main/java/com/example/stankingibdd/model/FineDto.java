package com.example.stankingibdd.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель штрафа")
public class FineDto {

    @Schema(description = "Идентификатор штрафа")
    private String fineId;

    @Schema(description = "Дата")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Schema(description = "Время")
    @DateTimeFormat(pattern = "HH:mm")
    private Date time;

    @Schema(description = "Место")
    private String location;

    @Schema(description = "Сумма")
    private int amount;

    @Schema(description = "Тип штрафа")
    private String type;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Статья")
    private String article;

    @Schema(description = "Регистрационный номер транспортного средства")
    private String vehicleRegistrationNumber;
}
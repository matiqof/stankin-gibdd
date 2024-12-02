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
@Schema(description = "Модель аварии")
public class AccidentDto {

    @Schema(description = "Идентификатор аварии")
    private String accidentId;

    @Schema(description = "Дата")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Schema(description = "Время")
    @DateTimeFormat(pattern = "HH:mm")
    private Date time;

    @Schema(description = "Место")
    private String location;

    @Schema(description = "Описание")
    private String description;
}
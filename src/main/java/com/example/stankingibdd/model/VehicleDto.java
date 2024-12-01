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
@Schema(description = "Модель транспортного средства")
public class VehicleDto {

    @Schema(description = "Регистрационный номер транспортного средства")
    private String registrationNumber;

    @Schema(description = "Модель транспортного средства")
    private String model;

    @Schema(description = "Производитель")
    private String manufacturer;

    @Schema(description = "Год выпуска")
    private int yearOfManufacture;

    @Schema(description = "Цвет")
    private String color;

    @Schema(description = "Пробег")
    private int mileage;

    @Schema(description = "Объем двигателя")
    private float engineVolume;

    @Schema(description = "Количество лошадиных сил")
    private int horsepower;

    @Schema(description = "Дата регистрации")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    @Schema(description = "Место регистрации")
    private String registrationLocation;

    @Schema(description = "Телефон клиента. Служит как username")
    private String phone;
}

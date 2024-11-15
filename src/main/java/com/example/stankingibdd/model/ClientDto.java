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
@Schema(description = "Модель клиента")
public class ClientDto {

    @Schema(description = "Телефон клиента. Служит как username")
    private String phone;

    @Schema(description = "Пароль клиента")
    private String clientPassword;

    @Schema(description = "ФИО")
    private String fullName;

    @Schema(description = "Дата рождения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Schema(description = "Адрес проживания")
    private String address;

    @Schema(description = "Паспорт")
    private String passport;

    @Schema(description = "Дата выдачи паспорта")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date passportIssueDate;

    @Schema(description = "Код подразделения паспорта")
    private int passportDepartmentCode;

    @Schema(description = "Роль клиент. По дефолту всегда просто user")
    private ClientRole role = ClientRole.ROLE_ADMIN;
}
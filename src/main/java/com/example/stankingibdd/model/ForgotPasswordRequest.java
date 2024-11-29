package com.example.stankingibdd.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель запроса на восстановление пароля")
public class ForgotPasswordRequest {

    @Schema(description = "Телефон клиента. Служит как username")
    private String phone;
}

package com.example.stankingibdd.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель запроса на сброс пароля")
public class ResetPasswordRequest {

    @Schema(description = "Токен клиента")
    private String token;

    @Schema(description = "Новый пароль")
    private String newPassword;

    @Schema(description = "Подтверждение пароля")
    private String confirmPassword;
}

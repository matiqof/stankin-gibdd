package com.example.stankingibdd.service;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;

public interface ClientService {

    /**
     * Генерация информации для восстановления пароля пользователя
     *
     * @param request модель запроса на восстановление пароля {@link ForgotPasswordRequest}
     * @param isFromProfile true/false в зависимости откуда пришел запроса, из профиля/логина
     */
    void forgotPassword(ForgotPasswordRequest request, boolean isFromProfile);

    /**
     * Генерация информации для восстановления пароля пользователя
     *
     * @param request модель запроса на сброс пароля {@link ResetPasswordRequest}
     */
    void resetPassword(ResetPasswordRequest request);

    /**
     * Сохранение изменений клиента в профиле
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void saveClient(ClientDto clientDto);
}

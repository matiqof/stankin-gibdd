package com.example.stankingibdd.service;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;

public interface ClientService {

    /**
     * Регистрация клиента
     *
     * @param clientDto модель клиента
     */
    void registerClient(ClientDto clientDto);

    /**
     * генерация информации для восстановления пароля пользователя
     *
     * @param request Модель запроса на восстановление пароля
     */
    void forgotPassword(ForgotPasswordRequest request);

    /**
     * генерация информации для восстановления пароля пользователя
     *
     * @param request Модель запроса на сброс пароля
     */
    void resetPassword(ResetPasswordRequest request);
}

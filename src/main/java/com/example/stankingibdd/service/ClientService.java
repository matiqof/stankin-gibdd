package com.example.stankingibdd.service;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;

public interface ClientService {

    /**
     * Регистрация клиента
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void registerClient(ClientDto clientDto);

    /**
     * Генерация информации для восстановления пароля пользователя
     *
     * @param request модель запроса на восстановление пароля {@link ForgotPasswordRequest}
     */
    void forgotPassword(ForgotPasswordRequest request);

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

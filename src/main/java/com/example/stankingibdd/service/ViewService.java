package com.example.stankingibdd.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public interface ViewService {

    /**
     * Получить template главной страницы
     *
     * @return template страницы
     */
    String getIndexPage();

    /**
     * Получить template страницы входа
     *
     * @return template страницы
     */
    String getLoginPage();

    /**
     * Получить template страницы регистрации
     *
     * @return template страницы
     */
    String getRegisterPage();

    /**
     * Получить template страницы генерации запроса на восстановление пароля
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getForgotPasswordPage(Model model);

    /**
     * Получить template страницы успешной генерации запроса на восстановление пароля
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getForgotPasswordSuccessPage(Model model);

    /**
     * Получить template страницы восстановления пароля
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getResetPasswordPage(Model model);

    /**
     * Получить template страницы успешного восстановления пароля
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getResetPasswordSuccessPage(Model model);

    /**
     * Получить template страницы профиля
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getProfilePage(Model model);

    /**
     * Получить template страницы генерации запроса на восстановление пароля в профиле
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getProfileForgotPasswordPage(Model model);

    /**
     * Получить template страницы успешной генерации запроса на восстановление пароля в профиле
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getProfileForgotPasswordSuccessPage(Model model);

    /**
     * Получить template страницы восстановления пароля в профиле
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getProfileResetPasswordPage(Model model);

    /**
     * Получить template страницы успешного восстановления пароля в профиле
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getProfileResetPasswordSuccessPage(Model model);

    /**
     * Получить template страницы ошибок
     *
     * @param model модель для отправки на ui {@link Model}
     * @param response модель с данными о запросе {@link HttpServletResponse}
     * @return template страницы
     */
    String getErrorPage(HttpServletResponse response, Model model);
}

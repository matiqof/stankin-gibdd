package com.example.stankingibdd.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public interface ViewService {

    /**
     * Получить template главной страницы
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getIndexPage(Model model);

    /**
     * Получить template страницы управления таблицей клиентов
     *
     * @param model модель для отправки на ui {@link Model}
     * @param phone фильтр по телефону клиента для списка клиентов
     * @return template страницы
     */
    String getClientsPage(String phone, Model model);

    /**
     * Получить template страницы управления таблицей водительских удостоверений
     *
     * @param model модель для отправки на ui {@link Model}
     * @param drivingLicenseNumber фильтр по номеру ВУ для списка водительских удостоверений
     * @return template страницы
     */
    String getDrivingLicensesPage(String drivingLicenseNumber, Model model);

    /**
     * Получить template страницы управления таблицей связей водетиельских удостоверений и категорий
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getDrivingLicenseCategoryLinksPage(Model model);

    /**
     * Получить template страницы управления таблицей транспротных средств
     *
     * @param model модель для отправки на ui {@link Model}
     * @param registrationNumber фильтр по регистрационному номеру для списка транспортных средств
     * @return template страницы
     */
    String getVehiclesPage(String registrationNumber, Model model);

    /**
     * Получить template страницы управления таблицей штрафов
     *
     * @param model модель для отправки на ui {@link Model}
     * @return template страницы
     */
    String getFinesPage(Model model);


    /**
     * Получить template страницы входа
     *
     * @return template страницы
     */
    String getLoginPage();

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

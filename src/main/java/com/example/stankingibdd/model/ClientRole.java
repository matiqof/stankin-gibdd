package com.example.stankingibdd.model;

import org.springframework.security.core.GrantedAuthority;

public enum ClientRole implements GrantedAuthority {

    /**
     * Пользователь, являющийся клиентом ГИБДД
     */
    ROLE_USER,

    /**
     * Администратор, владелец системы
     */
    ROLE_ADMIN,

    /**
     * Оператор регистрационного отдела
     */
    ROLE_OPERATOR,

    /**
     * Дорожный инспектор
     */
    ROLE_INSPECTOR,

    /**
     * Система фиксации нарушений
     */
    ROLE_SYSTEM;

    @Override
    public String getAuthority() {
        return name();
    }
}

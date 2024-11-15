package com.example.stankingibdd.util;

import java.util.HashMap;
import java.util.Map;

public class ControllersStaticData {

    public static final Map<String, String> profilePasswordRecoveryAttributes = new HashMap<>(Map.of("backLink", "/profile", "linkText", "Вспомнили пароль? Вернуться в профиль"));
    public static final Map<String, String> PasswordRecoveryAttributes = new HashMap<>(Map.of("backLink", "/login", "linkText", "Вспомнили пароль? Войти"));

}

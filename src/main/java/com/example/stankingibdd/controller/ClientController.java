package com.example.stankingibdd.controller;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;
import com.example.stankingibdd.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register")
    public String register(@ModelAttribute ClientDto clientDto) {
        try {
            clientService.registerClient(clientDto);
            return "redirect:/login";
        } catch (Exception e) {
            log.error("Ошибка регистрации клиента: " + e);
            return "redirect:/register?error";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest request) {
        try {
            clientService.forgotPassword(request);
            return "redirect:/forgot-password-success";
        } catch (Exception e) {
            log.error("Ошибка создания запроса на сброс пароля клиента: " + e);
            return "redirect:/forgot-password?error";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordRequest request) {
        try {
            clientService.resetPassword(request);
            return "redirect:/reset-password-success";
        } catch (Exception e) {
            log.error("Ошибка сброса пароля клиента: " + e);
            if (Objects.nonNull(request)) {
                return "redirect:/reset-password?token=" + request.getToken() + "&error";
            } else {
                return "redirect:/forgot-password";
            }
        }
    }
}
package com.example.stankingibdd.controller;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;
import com.example.stankingibdd.service.ClientService;
import com.example.stankingibdd.service.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

import static com.example.stankingibdd.util.ControllersStaticData.PasswordRecoveryAttributes;
import static com.example.stankingibdd.util.ControllersStaticData.profilePasswordRecoveryAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ViewService viewService;
    private final ClientService clientService;

    @PostMapping("/register")
    public String register(@ModelAttribute ClientDto clientDto, Model model) {
        try {
            clientService.registerClient(clientDto);
            return "login";
        } catch (Exception e) {
            log.error("Ошибка регистрации клиента: " + e);
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest request, Model model) {
        try {
            clientService.forgotPassword(request);
            return "forgot-password-success";
        } catch (Exception e) {
            log.error("Ошибка создания запроса на сброс пароля клиента: " + e);
            model.addAllAttributes(PasswordRecoveryAttributes);
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordRequest request, Model model) {
        try {
            clientService.resetPassword(request);
            return "reset-password-success";
        } catch (Exception e) {
            log.error("Ошибка сброса пароля клиента: " + e);
            if (Objects.nonNull(request)) {
                model.addAttribute("error", e.getMessage());
                model.addAllAttributes(PasswordRecoveryAttributes);
                return "redirect:/reset-password?token=" + request.getToken();
            } else {
                return "forgot-password";
            }
        }
    }

    @PostMapping("/profile-forgot-password")
    public String profileForgotPassword(@ModelAttribute ForgotPasswordRequest request, Model model) {
        try {
            clientService.forgotPassword(request);
            return "forgot-password-success";
        } catch (Exception e) {
            log.error("Ошибка создания запроса на сброс пароля клиента: " + e);
            model.addAllAttributes(profilePasswordRecoveryAttributes);
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    @PostMapping("/profile-reset-password")
    public String profileResetPassword(@ModelAttribute ResetPasswordRequest request, Model model) {
        try {
            clientService.resetPassword(request);
            return "reset-password-success";
        } catch (Exception e) {
            log.error("Ошибка сброса пароля клиента: " + e);
            if (Objects.nonNull(request)) {
                model.addAttribute("error", e.getMessage());
                model.addAllAttributes(profilePasswordRecoveryAttributes);
                return "redirect:/profile-reset-password?token=" + request.getToken();
            } else {
                return "forgot-password";
            }
        }
    }

    @PostMapping("/profile")
    public String profile(@ModelAttribute ClientDto clientDto, Model model) {
        try {
            clientService.saveClient(clientDto);
            model.addAttribute("successSaveClient", true);
        } catch (Exception e) {
            log.error("Ошибка сохранения изменений клиента: " + e);
            model.addAttribute("error", e.getMessage());
        }

        return viewService.getProfilePage(model);
    }
}
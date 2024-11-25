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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

import static com.example.stankingibdd.util.ControllersStaticData.PasswordRecoveryAttributes;
import static com.example.stankingibdd.util.ControllersStaticData.profilePasswordRecoveryAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ViewService viewService;
    private final ClientService clientService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute ForgotPasswordRequest request, RedirectAttributes redirectAttributes) {
        try {
            clientService.forgotPassword(request, false);
            return "redirect:/forgot-password-success";
        } catch (Exception e) {
            log.error("Ошибка создания запроса на сброс пароля клиента: " + e);
            redirectAttributes.addAllAttributes(PasswordRecoveryAttributes);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordRequest request, RedirectAttributes redirectAttributes) {
        try {
            clientService.resetPassword(request);
            return "redirect:/reset-password-success";
        } catch (Exception e) {
            log.error("Ошибка сброса пароля клиента: " + e);
            if (Objects.nonNull(request)) {
                redirectAttributes.addAllAttributes(PasswordRecoveryAttributes);
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/reset-password?token=" + request.getToken();
            } else {
                return "redirect:/forgot-password";
            }
        }
    }

    @PostMapping("/profile-forgot-password")
    public String profileForgotPassword(@ModelAttribute ForgotPasswordRequest request, RedirectAttributes redirectAttributes) {
        try {
            clientService.forgotPassword(request, true);
            return "redirect:/profile-forgot-password-success";
        } catch (Exception e) {
            log.error("Ошибка создания запроса на сброс пароля клиента: " + e);
            redirectAttributes.addAllAttributes(profilePasswordRecoveryAttributes);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile-forgot-password";
        }
    }

    @PostMapping("/profile-reset-password")
    public String profileResetPassword(@ModelAttribute ResetPasswordRequest request, RedirectAttributes redirectAttributes) {
        try {
            clientService.resetPassword(request);
            return "redirect:/profile-reset-password-success";
        } catch (Exception e) {
            log.error("Ошибка сброса пароля клиента: " + e);
            if (Objects.nonNull(request)) {
                redirectAttributes.addAllAttributes(profilePasswordRecoveryAttributes);
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/profile-reset-password?token=" + request.getToken();
            } else {
                return "redirect:/profile-forgot-password";
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
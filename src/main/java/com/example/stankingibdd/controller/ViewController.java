package com.example.stankingibdd.controller;

import com.example.stankingibdd.service.ViewService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ViewController implements ErrorController {

    private final ViewService viewService;

    @GetMapping("/")
    public String index(Model model) {
        return viewService.getIndexPage(model);
    }

    @GetMapping("/clients")
    public String clients(@RequestParam(required = false) String phone, Model model) {
        return viewService.getClientsPage(phone, model);
    }

    @GetMapping("/driving-licenses")
    public String drivingLicenses(@RequestParam(required = false) String drivingLicenseNumber, Model model) {
        return viewService.getDrivingLicensesPage(drivingLicenseNumber, model);
    }

    @GetMapping("/driving-license-category-links")
    public String drivingLicensesCategories(Model model) {
        return viewService.getDrivingLicenseCategoryLinksPage(model);
    }

    @GetMapping("/vehicles")
    public String vehicles(@RequestParam(required = false) String registrationNumber, Model model) {
        return viewService.getVehiclesPage(registrationNumber, model);
    }

    @GetMapping("/fines")
    public String fines(Model model) {
        return viewService.getFinesPage(model);
    }

    @GetMapping("/login")
    public String login() {
        return viewService.getLoginPage();
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return viewService.getForgotPasswordPage(model);
    }

    @GetMapping("/forgot-password-success")
    public String forgotPasswordSuccess(Model model) {
        return viewService.getForgotPasswordSuccessPage(model);
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model) {
        return viewService.getResetPasswordPage(model);
    }

    @GetMapping("/reset-password-success")
    public String resetPasswordSuccess(Model model) {
        return viewService.getResetPasswordSuccessPage(model);
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return viewService.getProfilePage(model);
    }

    @GetMapping("/profile-forgot-password")
    public String profileForgotPassword(Model model) {
        return viewService.getProfileForgotPasswordPage(model);
    }

    @GetMapping("/profile-forgot-password-success")
    public String profileForgotPasswordSuccess(Model model) {
        return viewService.getProfileForgotPasswordSuccessPage(model);
    }

    @GetMapping("/profile-reset-password")
    public String profileResetPassword(Model model) {
        return viewService.getProfileResetPasswordPage(model);
    }

    @GetMapping("/profile-reset-password-success")
    public String profileResetPasswordSuccess(Model model) {
        return viewService.getProfileResetPasswordSuccessPage(model);
    }

    @RequestMapping("/error")
    public String error(HttpServletResponse response, Model model) {
        return viewService.getErrorPage(response, model);
    }
}

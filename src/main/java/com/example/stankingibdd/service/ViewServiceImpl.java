package com.example.stankingibdd.service;

import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import static com.example.stankingibdd.util.ControllersStaticData.PasswordRecoveryAttributes;
import static com.example.stankingibdd.util.ControllersStaticData.profilePasswordRecoveryAttributes;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public String getIndexPage(Model model) {
        model.addAttribute("client", SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return "index";
    }

    @Override
    public String getClientsPage(Model model) {
        List<ClientDto> clientDtoList = clientMapper.map(clientRepository.findAll());
        model.addAttribute("clients", clientDtoList);
        model.addAttribute("client", SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return "clients";
    }

    @Override
    public String getLoginPage() {
        return "login";
    }

    @Override
    public String getForgotPasswordPage(Model model) {
        model.addAllAttributes(PasswordRecoveryAttributes);
        model.addAttribute("action", "/forgot-password");
        return "forgot-password";
    }

    @Override
    public String getForgotPasswordSuccessPage(Model model) {
        model.addAllAttributes(PasswordRecoveryAttributes);
        return "forgot-password-success";
    }

    @Override
    public String getResetPasswordPage(Model model) {
        model.addAllAttributes(PasswordRecoveryAttributes);
        model.addAttribute("action", "/reset-password");
        return "reset-password";
    }

    @Override
    public String getResetPasswordSuccessPage(Model model) {
        model.addAllAttributes(Map.of("backLink", "/login", "linkText", "Войти"));
        return "reset-password-success";
    }

    @Override
    public String getProfilePage(Model model) {
        model.addAttribute("client", SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        return "profile";
    }

    @Override
    public String getProfileForgotPasswordPage(Model model) {
        model.addAllAttributes(profilePasswordRecoveryAttributes);
        model.addAttribute("action", "/profile-forgot-password");
        return "forgot-password";
    }

    @Override
    public String getProfileForgotPasswordSuccessPage(Model model) {
        model.addAllAttributes(profilePasswordRecoveryAttributes);
        return "forgot-password-success";
    }

    @Override
    public String getProfileResetPasswordPage(Model model) {
        model.addAllAttributes(profilePasswordRecoveryAttributes);
        model.addAttribute("action", "/profile-reset-password");
        return "reset-password";
    }

    @Override
    public String getProfileResetPasswordSuccessPage(Model model) {
        model.addAllAttributes(profilePasswordRecoveryAttributes);
        return "reset-password-success";
    }

    @Override
    public String getErrorPage(HttpServletResponse response, Model model) {
        Integer statusCode = response.getStatus();
        model.addAttribute("statusCode", statusCode);
        return "error";
    }
}

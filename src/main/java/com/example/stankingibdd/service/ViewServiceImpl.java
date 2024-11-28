package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.DrivingLicense;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.mapper.DrivingLicenseMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.DrivingLicenseRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.stankingibdd.util.ControllersStaticData.PasswordRecoveryAttributes;
import static com.example.stankingibdd.util.ControllersStaticData.profilePasswordRecoveryAttributes;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    private final DrivingLicenseRepository drivingLicenseRepository;
    private final DrivingLicenseMapper drivingLicenseMapper;

    @Override
    public String getIndexPage(Model model) {
        model.addAttribute("currentClient", SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());

        return "index";
    }

    @Override
    public String getClientsPage(String drivingLicenseNumber, Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Client> clientList = (StringUtils.hasLength(drivingLicenseNumber)) ? clientRepository.findAll().stream()
                .filter(client -> {
                    if (Objects.nonNull(client.getDrivingLicense())) {
                        return drivingLicenseNumber.equals(client.getDrivingLicense().getLicenseNumber());
                    }

                    return false;
                }).toList() : clientRepository.findAll();
        Map<String, DrivingLicense> drivingLicenseByPhoneMap = clientList.stream()
                .filter(client -> Objects.nonNull(client.getDrivingLicense()))
                .collect(Collectors.toMap(Client::getPhone, Client::getDrivingLicense));
        List<ClientDto> clientDtoList = clientMapper.map(clientList).stream()
                .filter(clientDto -> !currentClient.getPhone().equals(clientDto.getPhone()))
                .peek(clientDto -> {
                    DrivingLicense drivingLicense = drivingLicenseByPhoneMap.get(clientDto.getPhone());
                    if (Objects.nonNull(drivingLicense)) {
                        clientDto.setLicenseNumber(drivingLicense.getLicenseNumber());
                    }
                })
                .toList();

        model.addAttribute("clients", clientDtoList);
        model.addAttribute("currentClient", currentClient);
        return "clients";
    }

    @Override
    public String getDrivingLicensesPage(String phone, Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<DrivingLicense> drivingLicenseList = (StringUtils.hasLength(phone)) ? drivingLicenseRepository.findAll().stream()
                .filter(drivingLicense -> {
            if (Objects.nonNull(drivingLicense.getClient())) {
                return phone.equals(drivingLicense.getClient().getPhone());
            }

            return false;
        }).toList() : drivingLicenseRepository.findAll();

        Map<String, Client> phoneByLicenseNumberMap = drivingLicenseList.stream()
                .filter(drivingLicense -> Objects.nonNull(drivingLicense.getClient()))
                .collect(Collectors.toMap(DrivingLicense::getLicenseNumber,DrivingLicense::getClient));
        List<DrivingLicenseDto> drivingLicenseDtoList = drivingLicenseMapper.map(drivingLicenseList).stream()
                .peek(drivingLicenseDto -> {
            Client client = phoneByLicenseNumberMap.get(drivingLicenseDto.getLicenseNumber());
            if (Objects.nonNull(client)) {
                drivingLicenseDto.setPhone(client.getPhone());
            }
        }).toList();

        model.addAttribute("drivingLicenses", drivingLicenseDtoList);
        model.addAttribute("currentClient", currentClient);
        return "driving-licenses";
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
        model.addAttribute("currentClient", SecurityContextHolder.getContext()
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

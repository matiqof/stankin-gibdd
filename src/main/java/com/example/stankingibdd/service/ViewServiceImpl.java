package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Accident;
import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.DrivingLicense;
import com.example.stankingibdd.entity.Fine;
import com.example.stankingibdd.entity.Vehicle;
import com.example.stankingibdd.mapper.AccidentMapper;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.mapper.DrivingLicenseMapper;
import com.example.stankingibdd.mapper.FineMapper;
import com.example.stankingibdd.mapper.VehicleMapper;
import com.example.stankingibdd.model.AccidentDto;
import com.example.stankingibdd.model.CategoryType;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.model.FineDto;
import com.example.stankingibdd.model.VehicleDto;
import com.example.stankingibdd.repository.AccidentRepository;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.DrivingLicenseRepository;
import com.example.stankingibdd.repository.FineRepository;
import com.example.stankingibdd.repository.VehicleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
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

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    private final AccidentRepository accidentRepository;
    private final AccidentMapper accidentMapper;

    @Override
    public String getIndexPage(Model model) {
        model.addAttribute("currentClient", SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());

        return "index";
    }

    @Override
    public String getClientsPage(String phone, Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Client> clientList = (StringUtils.hasLength(phone)) ? clientRepository.findAll().stream()
                .filter(client -> {
                    if (Objects.nonNull(client.getDrivingLicense())) {
                        return phone.equals(client.getPhone());
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
    public String getDrivingLicensesPage(String drivingLicenseNumber, Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<DrivingLicense> drivingLicenseList = (StringUtils.hasLength(drivingLicenseNumber)) ? drivingLicenseRepository.findAll().stream()
                .filter(drivingLicense -> {
            if (Objects.nonNull(drivingLicense.getClient())) {
                return drivingLicenseNumber.equals(drivingLicense.getLicenseNumber());
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
    public String getDrivingLicenseCategoryLinksPage(Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<DrivingLicense> drivingLicenseList = drivingLicenseRepository.findAll();

        List<DrivingLicenseCategoryLinkDto> drivingLicenseCategoryLinkDtoList = new ArrayList<>();
        drivingLicenseList.forEach(drivingLicense -> {
            if (StringUtils.hasLength(drivingLicense.getLicenseNumber())
                    && Objects.nonNull(drivingLicense.getClient())
                    && !CollectionUtils.isEmpty(drivingLicense.getDrivingLicenseCategories())) {
                drivingLicense.getDrivingLicenseCategories().forEach(drivingLicenseCategory -> {
                    if (Objects.nonNull(drivingLicenseCategory.getCategory())) {
                        DrivingLicenseCategoryLinkDto drivingLicenseCategoryLinkDto = DrivingLicenseCategoryLinkDto.builder()
                                .categoryName(CategoryType.valueOf(drivingLicenseCategory.getCategory().getCategoryName()))
                                .licenseNumber(drivingLicense.getLicenseNumber())
                                .phone(drivingLicense.getClient().getPhone())
                                .build();
                        drivingLicenseCategoryLinkDtoList.add(drivingLicenseCategoryLinkDto);
                    }
                });
            }
        });

        model.addAttribute("drivingLicenseCategories", drivingLicenseCategoryLinkDtoList);
        model.addAttribute("currentClient", currentClient);
        return "driving-license-category-links";
    }

    @Override
    public String getVehiclesPage(String registrationNumber, Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Vehicle> vehicles = (StringUtils.hasLength(registrationNumber)) ? vehicleRepository.findAll().stream().filter(vehicle -> {
            if (!CollectionUtils.isEmpty(vehicle.getFines())) {
                return registrationNumber.equals(vehicle.getRegistrationNumber());
            }

            return false;
        }).toList() : vehicleRepository.findAll();
        Map<String, String> registrationNumberByPhone = vehicles.stream()
                .collect(Collectors.toMap(Vehicle::getRegistrationNumber, vehicle -> vehicle.getClient().getPhone()));

        List<VehicleDto> vehicleDtoList = vehicleMapper.map(vehicles).stream()
                .peek(vehicleDto -> vehicleDto.setPhone(registrationNumberByPhone.get(vehicleDto.getRegistrationNumber())))
                .toList();

        model.addAttribute("vehicles", vehicleDtoList);
        model.addAttribute("currentClient", currentClient);
        return "vehicles";
    }

    @Override
    public String getFinesPage(Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Fine> fines = fineRepository.findAll();
        Map<UUID, String> registrationNumberByFineId = fines.stream()
                .collect(Collectors.toMap(Fine::getFineId, fine -> fine.getVehicle().getRegistrationNumber()));

        List<FineDto> fineDtoList = fineMapper.map(fines).stream()
                .peek(fineDto -> fineDto.setVehicleRegistrationNumber(registrationNumberByFineId.get(UUID.fromString(fineDto.getFineId()))))
                .toList();

        model.addAttribute("fines", fineDtoList);
        model.addAttribute("currentClient", currentClient);
        return "fines";
    }

    @Override
    public String getAccidentsPage(Model model) {
        Client currentClient = (Client) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        List<Accident> accidents = accidentRepository.findAll();
        List<AccidentDto> accidentDtoList = accidentMapper.map(accidents);

        model.addAttribute("accidents", accidentDtoList);
        model.addAttribute("currentClient", currentClient);
        return "accidents";
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

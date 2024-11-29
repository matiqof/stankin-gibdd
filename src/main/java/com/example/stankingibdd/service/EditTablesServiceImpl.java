package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Category;
import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.DrivingLicense;
import com.example.stankingibdd.entity.DrivingLicenseCategory;
import com.example.stankingibdd.exception.EditTablesException;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.mapper.DrivingLicenseMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.repository.CategoryRepository;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.DrivingLicenseCategoryRepository;
import com.example.stankingibdd.repository.DrivingLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EditTablesServiceImpl implements EditTablesService {

    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    private final DrivingLicenseRepository drivingLicenseRepository;
    private final DrivingLicenseMapper drivingLicenseMapper;

    private final DrivingLicenseCategoryRepository drivingLicenseCategoryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public void addClient(ClientDto clientDto) {
        if (Objects.isNull(clientDto)) {
            final String errorMessage = "Невозможно добавить пустого клиента";
            throw new EditTablesException(errorMessage);
        }

        String phone = clientDto.getPhone();
        if (clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с телефоном " + phone + " уже существует";
            throw new EditTablesException(errorMessage);
        }

        String passportNumber = clientDto.getPassportNumber();
        if (clientRepository.existsClientByPassportNumber(passportNumber)) {
            final String errorMessage = "Клиент с паспортом " + passportNumber + " уже существует";
            throw new EditTablesException(errorMessage);
        }

        Client client = clientMapper.map(clientDto);
        client.setClientPassword(passwordEncoder.encode(clientDto.getClientPassword()));

        clientRepository.save(client);
    }

    @Override
    public void editClient(ClientDto clientDto) {
        clientService.saveClient(clientDto);
    }

    @Override
    public void deleteClient(String phone) {
        if (!StringUtils.hasLength(phone)) {
            final String errorMessage = "Невозможно удалить клиента с пустым телефоном";
            throw new EditTablesException(errorMessage);
        }

        if (!clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с телефоном " + phone + " не существует";
            throw new EditTablesException(errorMessage);
        }

        Client client = clientRepository.findByPhone(phone);
        if (Objects.nonNull(client.getDrivingLicense())
                && drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(client.getDrivingLicense().getLicenseNumber())) {
            final String errorMessage = "У клиента с телефоном " + phone + " привязано водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        clientRepository.deleteByPhone(phone);
    }

    @Override
    public void addDrivingLicense(DrivingLicenseDto drivingLicenseDto) {
        if (Objects.isNull(drivingLicenseDto)) {
            final String errorMessage = "Невозможно добавить пустое водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        String licenseNumber = drivingLicenseDto.getLicenseNumber();
        if (drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(licenseNumber)) {
            final String errorMessage = "Водительское удостоверение с номером " + licenseNumber + " уже существует";
            throw new EditTablesException(errorMessage);
        }

        String phone = drivingLicenseDto.getPhone();
        Client client = checkClientByPhone(phone);

        DrivingLicense drivingLicense = drivingLicenseMapper.map(drivingLicenseDto);
        drivingLicense.setClient(client);

        drivingLicenseRepository.save(drivingLicense);
    }

    @Override
    public void editDrivingLicense(DrivingLicenseDto drivingLicenseDto) {
        if (Objects.isNull(drivingLicenseDto)) {
            final String errorMessage = "Невозможно добавить пустое водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        String licenseNumber = drivingLicenseDto.getLicenseNumber();
        if (!drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(licenseNumber)) {
            final String errorMessage = "Водительское удостоверение с номером " + licenseNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }


        DrivingLicense drivingLicenseFromDb = drivingLicenseRepository.findByLicenseNumber(licenseNumber);
        DrivingLicense drivingLicense = drivingLicenseMapper.map(drivingLicenseFromDb, drivingLicenseMapper.map(drivingLicenseDto));

        String phone = drivingLicenseDto.getPhone();
        if (Objects.nonNull(drivingLicense.getClient()) && !phone.equals(drivingLicense.getClient().getPhone())) {
            Client client = checkClientByPhone(phone);

            drivingLicense.setClient(client);
            drivingLicenseRepository.save(drivingLicense);
        }
    }

    @Override
    public void deleteDrivingLicense(String licenseNumber) {
        if (!StringUtils.hasLength(licenseNumber)) {
            final String errorMessage = "Невозможно удалить водительское удостоверение с пустым номером";
            throw new EditTablesException(errorMessage);
        }

        if (!drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(licenseNumber)) {
            final String errorMessage = "Водительское удостоверение с номером " + licenseNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        drivingLicenseRepository.deleteByLicenseNumber(licenseNumber);
    }

    private Client checkClientByPhone(String phone) {
        if (!clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с указанным номером телефона " + phone + " не существует";
            throw new EditTablesException(errorMessage);
        }

        Client client = clientRepository.findByPhone(phone);
        if (Objects.nonNull(client.getDrivingLicense())) {
            final String errorMessage = "Для клиент с указанным номером телефона " + phone + " уже существует водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        return client;
    }

    @Override
    public void addDrivingLicenseCategoryLink(DrivingLicenseCategoryLinkDto drivingLicenseCategoryLinkDto) {
        if (Objects.isNull(drivingLicenseCategoryLinkDto)) {
            final String errorMessage = "Невозможно добавить пустую связь водительского удостоверения и категории";
            throw new EditTablesException(errorMessage);
        }

        String categoryName = drivingLicenseCategoryLinkDto.getCategoryName().name();
        if (!categoryRepository.existsCategoryByCategoryName(categoryName)) {
            final String errorMessage = "Категория водительского удостоверения с названием " + categoryName + " не существует";
            throw new EditTablesException(errorMessage);
        }

        String licenseNumber = drivingLicenseCategoryLinkDto.getLicenseNumber();
        if (!drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(licenseNumber)) {
            final String errorMessage = "Водительское удостоверение с номером " + licenseNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        Category category = categoryRepository.findByCategoryName(categoryName);
        DrivingLicense drivingLicense = drivingLicenseRepository.findByLicenseNumber(licenseNumber);

        DrivingLicenseCategory drivingLicenseCategory = DrivingLicenseCategory.builder()
                .categoryId(category.getCategoryId())
                .category(category)
                .licenseId(drivingLicense.getLicenseId())
                .drivingLicense(drivingLicense)
                .build();
        drivingLicenseCategoryRepository.save(drivingLicenseCategory);
    }

    @Override
    public void deleteDrivingLicenseCategoryLink(String licenseNumber, String categoryName) {
        if (!StringUtils.hasLength(licenseNumber)) {
            final String errorMessage = "Невозможно удалить водительское удостоверение с пустым номером";
            throw new EditTablesException(errorMessage);
        }

        if (!StringUtils.hasLength(categoryName)) {
            final String errorMessage = "Невозможно удалить водительское удостоверение с пустым названием категории";
            throw new EditTablesException(errorMessage);
        }

        if (!drivingLicenseRepository.existsDrivingLicenseByLicenseNumber(licenseNumber)) {
            final String errorMessage = "Водительское удостоверение с номером " + licenseNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (!categoryRepository.existsCategoryByCategoryName(categoryName)) {
            final String errorMessage = "Категория водительского удостоверения с названием " + categoryName + " не существует";
            throw new EditTablesException(errorMessage);
        }

        drivingLicenseCategoryRepository.deleteByLicenseNumberAndCategoryName(licenseNumber, categoryName);
    }
}

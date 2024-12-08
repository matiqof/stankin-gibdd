package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Accident;
import com.example.stankingibdd.entity.AccidentComposition;
import com.example.stankingibdd.entity.Category;
import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.DrivingLicense;
import com.example.stankingibdd.entity.DrivingLicenseCategory;
import com.example.stankingibdd.entity.Fine;
import com.example.stankingibdd.entity.Vehicle;
import com.example.stankingibdd.exception.EditTablesException;
import com.example.stankingibdd.mapper.AccidentMapper;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.mapper.DrivingLicenseMapper;
import com.example.stankingibdd.mapper.FineMapper;
import com.example.stankingibdd.mapper.VehicleMapper;
import com.example.stankingibdd.model.AccidentCompositionDto;
import com.example.stankingibdd.model.AccidentDto;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.model.FineDto;
import com.example.stankingibdd.model.VehicleDto;
import com.example.stankingibdd.repository.AccidentCompositionRepository;
import com.example.stankingibdd.repository.AccidentRepository;
import com.example.stankingibdd.repository.CategoryRepository;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.DrivingLicenseCategoryRepository;
import com.example.stankingibdd.repository.DrivingLicenseRepository;
import com.example.stankingibdd.repository.FineRepository;
import com.example.stankingibdd.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    private final FineRepository fineRepository;
    private final FineMapper fineMapper;

    private final AccidentRepository accidentRepository;
    private final AccidentMapper accidentMapper;

    private final AccidentCompositionRepository accidentCompositionRepository;

    private static final Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

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

        if (clientDto.getPassportIssueDate().after(currentDate)) {
            final String errorMessage = "У клиента указана дата выдачи паспорта " + clientDto.getPassportIssueDate() + " больше текущей даты";
            throw new EditTablesException(errorMessage);
        }

        if (clientDto.getDateOfBirth().after(currentDate)) {
            final String errorMessage = "У клиента указана дата рождения " + clientDto.getDateOfBirth() + " больше текущей даты";
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
        if (Objects.nonNull(client.getDrivingLicense())) {
            final String errorMessage = "У клиента с телефоном " + phone + " привязано водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        if (!CollectionUtils.isEmpty(client.getVehicles())) {
            final String errorMessage = "У клиента с телефоном " + phone + " привязаны транспортные средства";
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

        if (drivingLicenseDto.getIssueDate().after(currentDate)) {
            final String errorMessage = "Дата выдачи у водительского удостоверения больше, чем текущая дата";
            throw new EditTablesException(errorMessage);
        }

        if (drivingLicenseDto.getIssueDate().after(drivingLicenseDto.getExpirationDate())) {
            final String errorMessage = "Дата выдачи у водительского удостоверения больше, чем дата истекания";
            throw new EditTablesException(errorMessage);
        }

        String phone = drivingLicenseDto.getPhone();
        checkClientByPhone(phone);
        Client client = clientRepository.findByPhone(phone);
        if (Objects.nonNull(client.getDrivingLicense())) {
            final String errorMessage = "Для клиент с указанным номером телефона " + phone + " уже существует водительское удостоверение";
            throw new EditTablesException(errorMessage);
        }

        LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateOfBirthday = client.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (Period.between(currentLocalDate, localDateOfBirthday).getYears() < 18) {
            final String errorMessage = "Клиенту с указанным номером телефона " + phone + " меньше 18 лет";
            throw new EditTablesException(errorMessage);
        }

        DrivingLicense drivingLicense = drivingLicenseMapper.map(drivingLicenseDto);
        drivingLicense.setClient(client);

        drivingLicenseRepository.save(drivingLicense);
    }

    @Override
    public void editDrivingLicense(DrivingLicenseDto drivingLicenseDto) {
        if (Objects.isNull(drivingLicenseDto)) {
            final String errorMessage = "Невозможно изменить пустое водительское удостоверение";
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
            checkClientByPhone(phone);
            Client client = clientRepository.findByPhone(phone);
            if (Objects.nonNull(client.getDrivingLicense())) {
                final String errorMessage = "Для клиент с указанным номером телефона " + phone + " уже существует водительское удостоверение";
                throw new EditTablesException(errorMessage);
            }

            LocalDate currentLocalDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateOfBirthday = client.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (Period.between(currentLocalDate, localDateOfBirthday).getYears() < 18) {
                final String errorMessage = "Клиенту с указанным номером телефона " + phone + " меньше 18 лет";
                throw new EditTablesException(errorMessage);
            }

            drivingLicense.setClient(client);
        }

        drivingLicenseRepository.save(drivingLicense);
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

        drivingLicenseCategoryRepository.deleteByLicenseNumber(licenseNumber);
        drivingLicenseRepository.deleteByLicenseNumber(licenseNumber);
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

        if (!drivingLicenseCategoryRepository.existsAnyDrivingLicenseCategoryByLicenseNumber(licenseNumber)) {
            final String errorMessage = "У водительского удостоверения " + licenseNumber + " не может не быть категорий";
            throw new EditTablesException(errorMessage);
        }

        drivingLicenseCategoryRepository.deleteByLicenseNumberAndCategoryName(licenseNumber, categoryName);
    }

    @Override
    public void addVehicle(VehicleDto vehicleDto) {
        if (Objects.isNull(vehicleDto)) {
            final String errorMessage = "Невозможно добавить пустое транспортное средство";
            throw new EditTablesException(errorMessage);
        }

        String registrationNumber = vehicleDto.getRegistrationNumber();
        if (vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " уже существует";
            throw new EditTablesException(errorMessage);
        }

        if (vehicleDto.getYearOfManufacture() > currentDate.getYear()) {
            final String errorMessage = "Год выпуска транспортного средства больше текущего года";
            throw new EditTablesException(errorMessage);
        }

        if (vehicleDto.getRegistrationDate().after(currentDate)) {
            final String errorMessage = "Дата регистрации у транспортного средства больше, чем текущая дата";
            throw new EditTablesException(errorMessage);
        }

        String phone = vehicleDto.getPhone();
        checkClientByPhone(phone);
        Client client = clientRepository.findByPhone(phone);

        Vehicle vehicle = vehicleMapper.map(vehicleDto);
        vehicle.setClient(client);

        vehicleRepository.save(vehicle);
    }

    @Override
    public void editVehicle(VehicleDto vehicleDto) {
        if (Objects.isNull(vehicleDto)) {
            final String errorMessage = "Невозможно изменить пустое транспортное средство";
            throw new EditTablesException(errorMessage);
        }

        String registrationNumber = vehicleDto.getRegistrationNumber();
        if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (vehicleDto.getYearOfManufacture() > currentDate.getYear()) {
            final String errorMessage = "Год выпуска транспортного средства больше текущего года";
            throw new EditTablesException(errorMessage);
        }

        if (vehicleDto.getRegistrationDate().after(currentDate)) {
            final String errorMessage = "Дата регистрации у транспортного средства больше, чем текущая дата";
            throw new EditTablesException(errorMessage);
        }

        Vehicle vehicleFromDb = vehicleRepository.findByRegistrationNumber(registrationNumber);
        Vehicle vehicle = vehicleMapper.map(vehicleFromDb, vehicleMapper.map(vehicleDto));

        String phone = vehicleDto.getPhone();
        if (Objects.nonNull(vehicle.getClient()) && !phone.equals(vehicle.getClient().getPhone())) {
            checkClientByPhone(phone);
            Client client = clientRepository.findByPhone(phone);

            vehicle.setClient(client);
        }

        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(String registrationNumber) {
        if (!StringUtils.hasLength(registrationNumber)) {
            final String errorMessage = "Невозможно удалить транспортное средства с пустым регистрационным знаком";
            throw new EditTablesException(errorMessage);
        }

        if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);
        if (!CollectionUtils.isEmpty(vehicle.getFines())) {
            final String errorMessage = "У транспортного средства с номером " + registrationNumber + " существуют штрафы";
            throw new EditTablesException(errorMessage);
        }

        if (!CollectionUtils.isEmpty(vehicle.getAccidentCompositions())) {
            final String errorMessage = "У транспортного средства с номером " + registrationNumber + " имеются ДТП";
            throw new EditTablesException(errorMessage);
        }

        vehicleRepository.deleteByRegistrationNumber(registrationNumber);
    }

    @Override
    public void addFine(FineDto fineDto) {
        if (Objects.isNull(fineDto)) {
            final String errorMessage = "Невозможно добавить пустой штраф";
            throw new EditTablesException(errorMessage);
        }

        String registrationNumber = fineDto.getVehicleRegistrationNumber();
        if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (fineDto.getDate().after(currentDate)) {
            final String errorMessage = "Дата штрафа у транспортного средства больше, чем текущая дата";
            throw new EditTablesException(errorMessage);
        }

        if (fineDto.getAmount() <= 0) {
            final String errorMessage = "У штраф указана сумма меньше 1";
            throw new EditTablesException(errorMessage);
        }

        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);

        Fine fine = fineMapper.mapWithoutId(fineDto);
        fine.setVehicle(vehicle);

        fineRepository.save(fine);
    }

    @Override
    public void editFine(FineDto fineDto) {
        if (Objects.isNull(fineDto)) {
            final String errorMessage = "Невозможно изменить пустой штраф";
            throw new EditTablesException(errorMessage);
        }

        UUID fineId = UUID.fromString(fineDto.getFineId());
        if (!fineRepository.existsFineByFineId(fineId)) {
            final String errorMessage = "Штраф с идентификатором " + fineId + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (fineDto.getDate().after(currentDate)) {
            final String errorMessage = "Дата штрафа у транспортного средства больше, чем текущая дата";
            throw new EditTablesException(errorMessage);
        }

        if (fineDto.getAmount() <= 0) {
            final String errorMessage = "У штраф указана сумма меньше 1";
            throw new EditTablesException(errorMessage);
        }

        Fine fineFromDb = fineRepository.findByFineId(fineId);
        Fine fine = fineMapper.map(fineFromDb, fineMapper.map(fineDto));

        String registrationNumber = fineDto.getVehicleRegistrationNumber();
        if (Objects.nonNull(fine.getVehicle()) && !registrationNumber.equals(fine.getVehicle().getRegistrationNumber())) {
            if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
                final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
                throw new EditTablesException(errorMessage);
            }

            Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);
            fine.setVehicle(vehicle);
        }

        fineRepository.save(fine);
    }

    @Override
    public void deleteFine(String fineId) {
        if (!StringUtils.hasLength(fineId)) {
            final String errorMessage = "Невозможно удалить штраф с пустым идентификатором";
            throw new EditTablesException(errorMessage);
        }

        UUID id = UUID.fromString(fineId);
        if (!fineRepository.existsFineByFineId(id)) {
            final String errorMessage = "Штраф с идентификатором " + fineId + " не существует";
            throw new EditTablesException(errorMessage);
        }

        fineRepository.deleteByFineId(id);
    }

    @Override
    public void addAccident(AccidentDto accidentDto) {
        if (Objects.isNull(accidentDto)) {
            final String errorMessage = "Невозможно добавить пустую аварию";
            throw new EditTablesException(errorMessage);
        }

        accidentRepository.save(accidentMapper.mapWithoutId(accidentDto));
    }

    @Override
    public void editAccident(AccidentDto accidentDto) {
        if (Objects.isNull(accidentDto)) {
            final String errorMessage = "Невозможно изменить пустую аварию";
            throw new EditTablesException(errorMessage);
        }

        UUID accidentId = UUID.fromString(accidentDto.getAccidentId());
        if (!accidentRepository.existsAccidentByAccidentId(accidentId)) {
            final String errorMessage = "Авария с идентификатором " + accidentId + " не существует";
            throw new EditTablesException(errorMessage);
        }

        Accident accidentFromDb = accidentRepository.findByAccidentId(accidentId);
        Accident accident = accidentMapper.map(accidentFromDb, accidentMapper.map(accidentDto));

        accidentRepository.save(accident);
    }

    @Override
    public void deleteAccident(String accidentId) {
        if (!StringUtils.hasLength(accidentId)) {
            final String errorMessage = "Невозможно удалить аварию с пустым идентификатором";
            throw new EditTablesException(errorMessage);
        }

        UUID id = UUID.fromString(accidentId);
        if (!accidentRepository.existsAccidentByAccidentId(id)) {
            final String errorMessage = "Авария с идентификатором " + accidentId + " не существует";
            throw new EditTablesException(errorMessage);
        }

        accidentCompositionRepository.deleteByAccidentId(id);
        accidentRepository.deleteByAccidentId(id);
    }

    @Override
    public void addAccidentComposition(AccidentCompositionDto accidentCompositionDto) {
        if (Objects.isNull(accidentCompositionDto)) {
            final String errorMessage = "Невозможно добавить пустую связть аварии и транспортного средства";
            throw new EditTablesException(errorMessage);
        }

        UUID id = UUID.fromString(accidentCompositionDto.getAccidentId());
        String registrationNumber = accidentCompositionDto.getRegistrationNumber();
        if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (!accidentRepository.existsAccidentByAccidentId(id)) {
            final String errorMessage = "Аварии с идентификатором " + accidentCompositionDto.getAccidentId() + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (accidentCompositionRepository.existsAccidentByAccidentIdAndRegistrationNumber(id, registrationNumber)) {
            final String errorMessage = "Связь аварии с идентификатором " + accidentCompositionDto.getAccidentId() + " и транспортного средства с регистрационным знаком " + registrationNumber + " уже существует";
            throw new EditTablesException(errorMessage);
        }

        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber);
        Accident accident = accidentRepository.findByAccidentId(id);

        AccidentComposition accidentComposition = AccidentComposition.builder()
                .accidentId(id)
                .accident(accident)
                .vehicleId(vehicle.getVehicleId())
                .vehicle(vehicle)
                .build();
        accidentCompositionRepository.save(accidentComposition);
    }

    @Override
    public void deleteAccidentComposition(String accidentId, String registrationNumber) {
        if (!StringUtils.hasLength(accidentId) || !StringUtils.hasLength(registrationNumber)) {
            final String errorMessage = "Невозможно удалить связь аварии и транспортного средства с пустыми входными данными";
            throw new EditTablesException(errorMessage);
        }

        UUID id = UUID.fromString(accidentId);
        if (!vehicleRepository.existsVehicleByRegistrationNumber(registrationNumber)) {
            final String errorMessage = "Транспортное средство с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        if (!accidentCompositionRepository.existsAccidentByAccidentIdAndRegistrationNumber(id, registrationNumber)) {
            final String errorMessage = "Связь аварии с идентификатором " + accidentId + " и транспортного средства с регистрационным знаком " + registrationNumber + " не существует";
            throw new EditTablesException(errorMessage);
        }

        accidentCompositionRepository.deleteByAccidentIdAndRegistrationNumber(id, registrationNumber);
    }

    /**
     * Проверка клиента на существование по номеру телефона
     *
     * @param phone телефон клиента
     */
    private void checkClientByPhone(String phone) {
        if (!clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с указанным номером телефона " + phone + " не существует";
            throw new EditTablesException(errorMessage);
        }
    }
}

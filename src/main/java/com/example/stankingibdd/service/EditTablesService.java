package com.example.stankingibdd.service;

import com.example.stankingibdd.model.AccidentCompositionDto;
import com.example.stankingibdd.model.AccidentDto;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.model.FineDto;
import com.example.stankingibdd.model.VehicleDto;

public interface EditTablesService {

    /**
     * Добавить нового клиента
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void addClient(ClientDto clientDto);

    /**
     * Изменить клиента
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void editClient(ClientDto clientDto);

    /**
     * Удалить клиента
     *
     * @param phone телефон клиента
     */
    void deleteClient(String phone);

    /**
     * Добавить новое водительское удостоверение
     *
     * @param drivingLicenseDto модель водительского удостоверения {@link DrivingLicenseDto}
     */
    void addDrivingLicense(DrivingLicenseDto drivingLicenseDto);

    /**
     * Изменить водительское удостоверение
     *
     * @param drivingLicenseDto модель водительского удостоверения {@link DrivingLicenseDto}
     */
    void editDrivingLicense(DrivingLicenseDto drivingLicenseDto);

    /**
     * Удалить водительское удостоверение
     *
     * @param licenseNumber номер водительского удостоверения
     */
    void deleteDrivingLicense(String licenseNumber);

    /**
     * Добавить новую связь водительского удостоверения и категории
     *
     * @param drivingLicenseCategoryLinkDto модель связи водительского удостоверения и категории {@link DrivingLicenseCategoryLinkDto}
     */
    void addDrivingLicenseCategoryLink(DrivingLicenseCategoryLinkDto drivingLicenseCategoryLinkDto);

    /**
     * Удалить связь водительского удостоверения и категории
     *
     * @param licenseNumber номер водительского удостоверения
     * @param categoryName навзание категории водительского удостоверения
     */
    void deleteDrivingLicenseCategoryLink(String licenseNumber, String categoryName);

    /**
     * Добавить новое транспортное средства
     *
     * @param vehicleDto модель транспортного средства {@link VehicleDto}
     */
    void addVehicle(VehicleDto vehicleDto);

    /**
     * Изменить транспортное средства
     *
     * @param vehicleDto модель транспортного средства {@link VehicleDto}
     */
    void editVehicle(VehicleDto vehicleDto);

    /**
     * Удалить транспортное средства
     *
     * @param registrationNumber регистрационный номер транспортного средства
     */
    void deleteVehicle(String registrationNumber);

    /**
     * Добавить новый штраф
     *
     * @param fineDto модель штрафа {@link FineDto}
     */
    void addFine(FineDto fineDto);

    /**
     * Изменить штраф
     *
     * @param fineDto модель штрафа {@link FineDto}
     */
    void editFine(FineDto fineDto);

    /**
     * Удалить штраф
     *
     * @param fineId идентификатор штрафа
     */
    void deleteFine(String fineId);

    /**
     * Добавить новую аварию
     *
     * @param accidentDto модель аварии {@link AccidentDto}
     */
    void addAccident(AccidentDto accidentDto);

    /**
     * Изменить аварию
     *
     * @param accidentDto модель аварии {@link AccidentDto}
     */
    void editAccident(AccidentDto accidentDto);

    /**
     * Удалить аварию
     *
     * @param accidentId идентификатор аварии
     */
    void deleteAccident(String accidentId);

    /**
     * Добавить новую связь аварии и транспортного средства
     *
     * @param accidentCompositionDto модель связи аварии и транспортного средства {@link AccidentCompositionDto}
     */
    void addAccidentComposition(AccidentCompositionDto accidentCompositionDto);

    /**
     * Удалить связь аварии и транспортного средства
     *
     * @param accidentId идентификатор аварии
     * @param registrationNumber регистрационный номер транспортного средства
     */
    void deleteAccidentComposition(String accidentId, String registrationNumber);
}

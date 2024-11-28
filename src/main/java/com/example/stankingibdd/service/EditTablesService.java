package com.example.stankingibdd.service;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseDto;

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
}

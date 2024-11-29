package com.example.stankingibdd.controller;

import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.service.EditTablesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EditTablesController {

    private final EditTablesService editTablesService;

    @PostMapping("/client/add")
    public String addClient(@ModelAttribute ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addClient(clientDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление нового клиента!");
        } catch (Exception e) {
            log.error("Ошибка сохранения нового клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление нового клиента из-за ошибки: " + e.getMessage());
        }

        return "redirect:/clients";
    }

    @PostMapping("/client/edit")
    public String editClient(@ModelAttribute ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.editClient(clientDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное изменение клиента!");
        } catch (Exception e) {
            log.error("Ошибка изменения клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить изменение клиента из-за ошибки: " + e.getMessage());
        }

        return "redirect:/clients";
    }

    @PostMapping("/client/delete")
    public String deleteClient(@RequestParam String phone, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteClient(phone);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнение успешное удаление клиента!");
        } catch (Exception e) {
            log.error("Ошибка удаления клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление клиента из-за ошибки: " + e.getMessage());
        }

        return "redirect:/clients";
    }

    @PostMapping("/driving-license/add")
    public String addDrivingLicense(@ModelAttribute DrivingLicenseDto drivingLicenseDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addDrivingLicense(drivingLicenseDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление нового водительского удостоверения!");
        } catch (Exception e) {
            log.error("Ошибка сохранения нового клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление нового водительского удостоверения из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-licenses";
    }

    @PostMapping("/driving-license/edit")
    public String editDrivingLicense(@ModelAttribute DrivingLicenseDto drivingLicenseDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.editDrivingLicense(drivingLicenseDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное изменение водительского удостоверения!");
        } catch (Exception e) {
            log.error("Ошибка изменения клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить изменение водительского удостоверения из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-licenses";
    }

    @PostMapping("/driving-license/delete")
    public String deleteDrivingLicense(@RequestParam String licenseNumber, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteDrivingLicense(licenseNumber);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнение успешное удаление водительского удостоверения!");
        } catch (Exception e) {
            log.error("Ошибка удаления клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление водительского удостоверения из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-licenses";
    }

    @PostMapping("/driving-license-category-link/add")
    public String addDrivingLicenseCategoryLink(@ModelAttribute DrivingLicenseCategoryLinkDto drivingLicenseCategoryLinkDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addDrivingLicenseCategoryLink(drivingLicenseCategoryLinkDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление новой связи водительского удостоверения и категории!");
        } catch (Exception e) {
            log.error("Ошибка сохранения нового клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление новой связи водительского удостоверения и категории из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-license-category-links";
    }

    @PostMapping("/driving-license-category-link/delete")
    public String deleteDrivingLicenseCategoryLink(@RequestParam String licenseNumber, @RequestParam String categoryName, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteDrivingLicenseCategoryLink(licenseNumber, categoryName);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнение успешное удаление связи водительского удостоверения и категории!");
        } catch (Exception e) {
            log.error("Ошибка удаления клиента: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление связи водительского удостоверения и категории из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-license-category-links";
    }
}

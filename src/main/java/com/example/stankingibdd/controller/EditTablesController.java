package com.example.stankingibdd.controller;

import com.example.stankingibdd.model.AccidentCompositionDto;
import com.example.stankingibdd.model.AccidentDto;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.DrivingLicenseCategoryLinkDto;
import com.example.stankingibdd.model.DrivingLicenseDto;
import com.example.stankingibdd.model.FineDto;
import com.example.stankingibdd.model.VehicleDto;
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
            log.error("Ошибка сохранения нового водительского удостверения: " + e);
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
            log.error("Ошибка изменения водительского удостверения: " + e);
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
            log.error("Ошибка удаления водительского удостверения: " + e);
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
            log.error("Ошибка сохранения новой связи категории и водительского удостверения: " + e);
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
            log.error("Ошибка удаления связи категории и водительского удостверения: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление связи водительского удостоверения и категории из-за ошибки: " + e.getMessage());
        }

        return "redirect:/driving-license-category-links";
    }

    @PostMapping("/vehicle/add")
    public String addVehicle(@ModelAttribute VehicleDto vehicleDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addVehicle(vehicleDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление нового транспортного средства!");
        } catch (Exception e) {
            log.error("Ошибка сохранения нового транспортного средства: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление нового транспортного средства из-за ошибки: " + e.getMessage());
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/vehicle/edit")
    public String editVehicle(@ModelAttribute VehicleDto vehicleDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.editVehicle(vehicleDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное изменение транспортного средства!");
        } catch (Exception e) {
            log.error("Ошибка изменения транспортного средства: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить изменение транспортного средства из-за ошибки: " + e.getMessage());
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam String registrationNumber, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteVehicle(registrationNumber);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнение успешное удаление транспортного средства!");
        } catch (Exception e) {
            log.error("Ошибка удаления транспортного средства: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление транспортного средства из-за ошибки: " + e.getMessage());
        }

        return "redirect:/vehicles";
    }

    @PostMapping("/fine/add")
    public String addFine(@ModelAttribute FineDto fineDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addFine(fineDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление нового штрафа!");
        } catch (Exception e) {
            log.error("Ошибка сохранения нового штрафа: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление нового штрафа из-за ошибки: " + e.getMessage());
        }

        return "redirect:/fines";
    }

    @PostMapping("/fine/edit")
    public String editFine(@ModelAttribute FineDto fineDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.editFine(fineDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное изменение штрафа!");
        } catch (Exception e) {
            log.error("Ошибка изменения штрафа: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить изменение штрафа из-за ошибки: " + e.getMessage());
        }

        return "redirect:/fines";
    }

    @PostMapping("/fine/delete")
    public String deleteFine(@RequestParam String fineId, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteFine(fineId);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнение успешное удаление штрафа!");
        } catch (Exception e) {
            log.error("Ошибка удаления штрафа: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление штрафа из-за ошибки: " + e.getMessage());
        }

        return "redirect:/fines";
    }

    @PostMapping("/accident/add")
    public String addAccident(@ModelAttribute AccidentDto accidentDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addAccident(accidentDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление новой аварии!");
        } catch (Exception e) {
            log.error("Ошибка сохранения новой аварии: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление новой аварии из-за ошибки: " + e.getMessage());
        }

        return "redirect:/accidents";
    }

    @PostMapping("/accident/edit")
    public String editAccident(@ModelAttribute AccidentDto accidentDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.editAccident(accidentDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное изменение аварии!");
        } catch (Exception e) {
            log.error("Ошибка изменения аварии: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить изменение аварии из-за ошибки: " + e.getMessage());
        }

        return "redirect:/accidents";
    }

    @PostMapping("/accident/delete")
    public String deleteAccident(@RequestParam String accidentId, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteAccident(accidentId);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное удаление аварии!");
        } catch (Exception e) {
            log.error("Ошибка удаления аварии: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление аварии из-за ошибки: " + e.getMessage());
        }

        return "redirect:/accidents";
    }

    @PostMapping("/accident-composition/add")
    public String addAccidentComposition(@ModelAttribute AccidentCompositionDto accidentCompositionDto, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.addAccidentComposition(accidentCompositionDto);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное добавление новой связи аварии и транспортного средства!");
        } catch (Exception e) {
            log.error("Ошибка сохранения новой связи аварии и транспортного средства: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить добавление новой связи аварии и транспортного средства из-за ошибки: " + e.getMessage());
        }

        return "redirect:/accident-compositions";
    }

    @PostMapping("/accident-composition/delete")
    public String deleteAccidentComposition(@RequestParam String accidentId, @RequestParam String registrationNumber, RedirectAttributes redirectAttributes) {
        try {
            editTablesService.deleteAccidentComposition(accidentId, registrationNumber);
            redirectAttributes.addFlashAttribute("successSaveData", "Выполнено успешное удаление связи аварии и транспортного средства!");
        } catch (Exception e) {
            log.error("Ошибка удаления связи аварии и транспортного средства: " + e);
            redirectAttributes.addFlashAttribute("errorSaveData", "Невозможно выполнить удаление связи аварии и транспортного средства из-за ошибки: " + e.getMessage());
        }

        return "redirect:/accident-compositions";
    }
}

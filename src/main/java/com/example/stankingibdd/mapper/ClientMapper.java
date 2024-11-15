package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.model.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    @Mapping(target = "clientPassword", ignore = true)
    @Mapping(target = "clientNumber", ignore = true)
    @Mapping(target = "licenseNumber", ignore = true)
    @Mapping(target = "drivingLicense", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    public abstract Client map(ClientDto source);

    public Client map(Client source, Client external) {
        if (Objects.nonNull(external)) {
            if (StringUtils.hasLength(external.getFullName())) {
                source.setFullName(external.getFullName());
            }
            if (Objects.nonNull(external.getDateOfBirth())) {
                source.setDateOfBirth(external.getDateOfBirth());
            }
            if (StringUtils.hasLength(external.getAddress())) {
                source.setAddress(external.getAddress());
            }
            if (StringUtils.hasLength(external.getPassport())) {
                source.setPassport(external.getPassport());
            }
            if (Objects.nonNull(external.getPassportIssueDate())) {
                source.setPassportIssueDate(external.getPassportIssueDate());
            }
            if (external.getPassportDepartmentCode() != 0) {
                source.setPassportDepartmentCode(external.getPassportDepartmentCode());
            }
        }

        return source;
    }
}

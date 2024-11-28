package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.model.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

    @Mapping(target = "clientPassword", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "licenseId", ignore = true)
    @Mapping(target = "drivingLicense", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    public abstract Client map(ClientDto source);


    @Mapping(target = "licenseNumber", ignore = true)
    public abstract ClientDto map(Client source);

    public abstract List<ClientDto> map(List<Client> source);

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
            if (StringUtils.hasLength(external.getPassportNumber())) {
                source.setPassportNumber(external.getPassportNumber());
            }
            if (Objects.nonNull(external.getPassportIssueDate())) {
                source.setPassportIssueDate(external.getPassportIssueDate());
            }
            if (external.getPassportDepartmentCode() != 0) {
                source.setPassportDepartmentCode(external.getPassportDepartmentCode());
            }
            if (Objects.nonNull(external.getRole()) && StringUtils.hasLength(external.getRole().name())) {
                source.setRole(external.getRole());
            }
        }

        return source;
    }
}

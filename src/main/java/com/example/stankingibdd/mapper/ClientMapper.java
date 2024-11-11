package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.model.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "clientPassword", ignore = true)
    @Mapping(target = "clientNumber", ignore = true)
    @Mapping(target = "licenseNumber", ignore = true)
    @Mapping(target = "drivingLicense", ignore = true)
    @Mapping(target = "vehicles", ignore = true)
    Client map(ClientDto source);
}

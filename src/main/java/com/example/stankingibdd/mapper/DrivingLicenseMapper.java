package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.DrivingLicense;
import com.example.stankingibdd.model.DrivingLicenseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class DrivingLicenseMapper {

    @Mapping(target = "licenseId", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "drivingLicenseCategories", ignore = true)
    public abstract DrivingLicense map(DrivingLicenseDto source);

    @Mapping(target = "phone", ignore = true)
    public abstract DrivingLicenseDto map(DrivingLicense source);

    public abstract List<DrivingLicenseDto> map(List<DrivingLicense> source);

    public DrivingLicense map(DrivingLicense source, DrivingLicense external) {
        if (Objects.nonNull(external)) {
            if (StringUtils.hasLength(external.getLicenseNumber())) {
                source.setLicenseNumber(external.getLicenseNumber());
            }
            if (Objects.nonNull(external.getIssueDate())) {
                source.setIssueDate(external.getIssueDate());
            }
            if (Objects.nonNull(external.getExpirationDate())) {
                source.setExpirationDate(external.getExpirationDate());
            }
        }

        return source;
    }
}

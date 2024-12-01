package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Vehicle;
import com.example.stankingibdd.model.VehicleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class VehicleMapper {

    @Mapping(target = "vehicleId", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "accidentCompositions", ignore = true)
    @Mapping(target = "fines", ignore = true)
    public abstract Vehicle map(VehicleDto source);

    @Mapping(target = "phone", ignore = true)
    public abstract VehicleDto map(Vehicle source);

    public abstract List<VehicleDto> map(List<Vehicle> source);

    public Vehicle map(Vehicle source, Vehicle external) {
        if (Objects.nonNull(external)) {
            if (Objects.nonNull(external.getRegistrationNumber())) {
                source.setRegistrationNumber(external.getRegistrationNumber());
            }
            if (Objects.nonNull(external.getModel())) {
                source.setModel(external.getModel());
            }
            if (Objects.nonNull(external.getManufacturer())) {
                source.setManufacturer(external.getManufacturer());
            }
            if (external.getYearOfManufacture() != 0) {
                source.setYearOfManufacture(external.getYearOfManufacture());
            }
            if (Objects.nonNull(external.getColor())) {
                source.setColor(external.getColor());
            }
            if (external.getMileage() != 0) {
                source.setMileage(external.getMileage());
            }
            if (external.getEngineVolume() != 0) {
                source.setEngineVolume(external.getEngineVolume());
            }
            if (external.getHorsepower() != 0) {
                source.setHorsepower(external.getHorsepower());
            }
            if (Objects.nonNull(external.getRegistrationDate())) {
                source.setRegistrationDate(external.getRegistrationDate());
            }
            if (Objects.nonNull(external.getRegistrationLocation())) {
                source.setRegistrationLocation(external.getRegistrationLocation());
            }
        }

        return source;
    }
}

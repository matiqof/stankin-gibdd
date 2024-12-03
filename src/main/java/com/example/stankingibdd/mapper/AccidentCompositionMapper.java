package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.AccidentComposition;
import com.example.stankingibdd.model.AccidentCompositionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AccidentCompositionMapper {

    @Mapping(target = "registrationNumber", source = "source.vehicle.registrationNumber")
    public abstract AccidentCompositionDto map(AccidentComposition source);

    public abstract List<AccidentCompositionDto> map(List<AccidentComposition> source);
}
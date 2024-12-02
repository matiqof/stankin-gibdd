package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Accident;
import com.example.stankingibdd.model.AccidentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class AccidentMapper {

    @Mapping(target = "accidentCompositions", ignore = true)
    public abstract Accident map(AccidentDto source);

    @Mapping(target = "accidentCompositions", ignore = true)
    @Mapping(target = "accidentId", ignore = true)
    public abstract Accident mapWithoutId(AccidentDto source);

    public abstract AccidentDto map(Accident source);

    public abstract List<AccidentDto> map(List<Accident> source);

    public Accident map(Accident source, Accident external) {
        if (Objects.nonNull(external)) {
            if (Objects.nonNull(external.getLocation())) {
                source.setLocation(external.getLocation());
            }
            if (Objects.nonNull(external.getDate())) {
                source.setDate(external.getDate());
            }
            if (Objects.nonNull(external.getTime())) {
                source.setTime(external.getTime());
            }
            if (Objects.nonNull(external.getDescription())) {
                source.setDescription(external.getDescription());
            }
        }

        return source;
    }
}
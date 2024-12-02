package com.example.stankingibdd.mapper;

import com.example.stankingibdd.entity.Fine;
import com.example.stankingibdd.model.FineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class FineMapper {

    @Mapping(target = "vehicle", ignore = true)
    public abstract Fine map(FineDto source);

    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "fineId", ignore = true)
    public abstract Fine mapWithoutId(FineDto source);

    @Mapping(target = "vehicleRegistrationNumber", ignore = true)
    public abstract FineDto map(Fine source);

    public abstract List<FineDto> map(List<Fine> source);

    public Fine map(Fine source, Fine external) {
        if (Objects.nonNull(external)) {
            if (Objects.nonNull(external.getDate())) {
                source.setDate(external.getDate());
            }
            if (Objects.nonNull(external.getTime())) {
                source.setTime(external.getTime());
            }
            if (Objects.nonNull(external.getLocation())) {
                source.setLocation(external.getLocation());
            }
            if (external.getAmount() != 0) {
                source.setAmount(external.getAmount());
            }
            if (Objects.nonNull(external.getType())) {
                source.setType(external.getType());
            }
            if (Objects.nonNull(external.getDescription())) {
                source.setDescription(external.getDescription());
            }
            if (Objects.nonNull(external.getArticle())) {
                source.setArticle(external.getArticle());
            }
        }

        return source;
    }
}
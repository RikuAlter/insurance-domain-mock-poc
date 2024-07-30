package com.insurance.policy_manager.mapper;

import com.insurance.policy_manager.dto.CoverageDTO;
import com.insurance.policy_manager.entity.Coverage;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoverageMapper {

    CoverageMapper MAPPER = Mappers.getMapper(CoverageMapper.class);

    @Named("toDTO")
    @Mappings({
            @Mapping(source = "id", target = "coverageId"),
            @Mapping(source = "type", target = "coverageType"),
            @Mapping(source = "coverageAmount", target = "exposure"),
            @Mapping(source = "umbrellaAmount", target = "umbrellaCoverage"),
    })
    public CoverageDTO toDTO(final Coverage coverage);

    @Named("toEntity")
    @InheritInverseConfiguration
    public Coverage toEntity(final CoverageDTO coverageDTO);

    @Named("toDTOList")
    @IterableMapping(qualifiedByName = "toDTO")
    public List<CoverageDTO> toDTOList(final List<Coverage> coverages);

    @Named("toEntityList")
    @IterableMapping(qualifiedByName = "toEntity")
    public List<Coverage> toEntityList(final List<CoverageDTO> coverages);
}

package com.insurance.policy_manager.mapper;

import com.insurance.policy_manager.dto.LiabilityDTO;
import com.insurance.policy_manager.entity.Liability;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LiabilityMapper {

    LiabilityMapper MAPPER = Mappers.getMapper(LiabilityMapper.class);

    @Named("toDTO")
    @Mappings({
            @Mapping(source = "id", target = "liabilityId"),
            @Mapping(source = "type", target = "liabilityType"),
            @Mapping(source = "liabilityAmount", target= "exposure"),
    })
    public LiabilityDTO toDTO(final Liability liability);

    @Named("toEntity")
    @InheritInverseConfiguration
    public Liability toEntity(final LiabilityDTO liabilityDTO);

    @Named("toDTOList")
    @IterableMapping(qualifiedByName = "toDTO")
    public List<LiabilityDTO> toDTOList(final List<Liability> liabilities);

    @Named("toEntityList")
    @IterableMapping(qualifiedByName = "toEntity")
    public List<Liability> toEntityList(final List<LiabilityDTO> liabilities);
}

package com.insurance.policy_manager.mapper;

import com.insurance.policy_manager.dto.CoverageDTO;
import com.insurance.policy_manager.dto.LiabilityDTO;
import com.insurance.policy_manager.dto.PolicyDTO;
import com.insurance.policy_manager.entity.Coverage;
import com.insurance.policy_manager.entity.Liability;
import com.insurance.policy_manager.entity.Policy;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LiabilityMapper.class, CoverageMapper.class})
public interface PolicyMapper {

    @Named("toDTO")
    @Mappings({
            @Mapping(source = "id", target = "policyId"),
            @Mapping(source = "type", target = "policyType"),
            @Mapping(source = "premium", target = "premiumAmount"),
            @Mapping(source = "tenure", target = "policyTenure"),
            @Mapping(source = "publishedDate", target = "policyPublishedDate", dateFormat = "dd.MM.yyyy"),
            @Mapping(source = "lastUpdateDate", target = "policyUpdateDate", dateFormat = "dd.MM.yyyy"),
            @Mapping(source = "coverages", target = "coverages", qualifiedByName = "mapCoverageEntitiesToDTOs"),
            @Mapping(source = "liabilities", target = "liabilities", qualifiedByName = "mapLiabilityEntitiesToDTOs")
    })
    public PolicyDTO toDTO(final Policy policy);

    @Named("toEntity")
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(source = "coverages", target = "coverages", qualifiedByName = "mapCoverageDTOsToEntities"),
            @Mapping(source = "liabilities", target = "liabilities", qualifiedByName = "mapLiabilityDTOsToEntities")
    })
    public Policy toEntity(final PolicyDTO policyDTO);

    @Named("toDTOList")
    @IterableMapping(qualifiedByName = "toDTO")
    public List<PolicyDTO> toDTOList(final List<Policy> policies);

    @Named("toEntityList")
    @IterableMapping(qualifiedByName = "toEntity")
    public List<Policy> toEntityList(final List<PolicyDTO> policies);

    @Named("mapCoverageDTOsToEntities")
    default List<Coverage> mapCoverageDTOListToEntityList(final List<CoverageDTO> coverages){
        return CoverageMapper.MAPPER.toEntityList(coverages);
    }

    @Named("mapCoverageEntitiesToDTOs")
    default List<CoverageDTO> mapCoverageEntityListToDTOList(final List<Coverage> coverages){
        return CoverageMapper.MAPPER.toDTOList(coverages);
    }

    @Named("mapLiabilityDTOsToEntities")
    default List<Liability> mapLiabilityDTOListToEntityList(final List<LiabilityDTO> liabilities){
        return LiabilityMapper.MAPPER.toEntityList(liabilities);
    }

    @Named("mapLiabilityEntitiesToDTOs")
    default List<LiabilityDTO> mapLiabilityEntityListToDTOList(final List<Liability> liabilities){
        return LiabilityMapper.MAPPER.toDTOList(liabilities);
    }
}

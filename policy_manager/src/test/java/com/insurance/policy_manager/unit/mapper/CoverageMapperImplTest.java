package com.insurance.policy_manager.unit.mapper;

import com.insurance.policy_manager.dto.CoverageDTO;
import com.insurance.policy_manager.entity.Coverage;
import com.insurance.policy_manager.mapper.CoverageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoverageMapperImplTest {

    CoverageMapper coverageMapper = CoverageMapper.MAPPER;

    CoverageDTO coverageDTO;

    Coverage coverageEntity;

    @BeforeEach
    void setUp() {
        coverageDTO = CoverageDTO.builder()
                .coverageId(1000003L)
                .coverageType("Self")
                .exposure(BigDecimal.valueOf(1000000))
                .umbrellaCoverage(BigDecimal.valueOf(9000000))
                .build();
        coverageEntity = Coverage.builder()
                .id(1000003L)
                .type("Self")
                .coverageAmount(BigDecimal.valueOf(1000000))
                .umbrellaAmount(BigDecimal.valueOf(9000000))
                .build();
    }

    @Test
    void toDTO() {
        CoverageDTO convertedDTO = coverageMapper.toDTO(coverageEntity);
        assertEquals(coverageDTO, convertedDTO);
    }

    private void assertTrue() {
    }

    @Test
    void toEntity() {
        Coverage convertedEntity = coverageMapper.toEntity(coverageDTO);
        assertEquals(coverageEntity, convertedEntity);
    }

    @Test
    void toDTOList() {
        List<CoverageDTO> coverageDTOList = coverageMapper.toDTOList(List.of(coverageEntity));
        assertEquals(coverageDTOList.get(0), coverageDTO);
    }

    @Test
    void toEntityList() {
        List<Coverage> coverageList = coverageMapper.toEntityList(List.of(coverageDTO));
        assertEquals(coverageList.get(0), coverageEntity);
    }

}
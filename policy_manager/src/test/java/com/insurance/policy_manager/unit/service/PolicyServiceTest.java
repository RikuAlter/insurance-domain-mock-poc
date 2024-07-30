package com.insurance.policy_manager.unit.service;

import com.insurance.policy_manager.dto.PolicyDTO;
import com.insurance.policy_manager.entity.Policy;
import com.insurance.policy_manager.mapper.PolicyMapper;
import com.insurance.policy_manager.repository.PolicyRepository;
import com.insurance.policy_manager.repository.specification.PolicyAllMatchSpecification;
import com.insurance.policy_manager.repository.specification.PolicyAnyMatchSpecification;
import com.insurance.policy_manager.service.PolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PolicyServiceTest {

    private static final UUID SAVED_POLICY_ID = UUID.randomUUID();

    @Mock
    private PolicyRepository mockPolicyRepository;
    @Mock
    private PolicyMapper mockPolicyMapper;
    @Mock
    private Policy mockPolicy, mockSavedPolicy;
    @Mock
    private PolicyDTO mockPolicyDTO;
    @Mock
    private PolicyAllMatchSpecification mockAllMatchSpecification;
    @Mock
    private PolicyAnyMatchSpecification mockAnyMatchSpecification;
    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void findPolicyForAllSpecificationMatch() {
        List<Policy> policiesFound = List.of(mockPolicy);
        List<PolicyDTO> policyDTOList = List.of(mockPolicyDTO);

        when(mockPolicyRepository.findAll(mockAllMatchSpecification)).thenReturn(policiesFound);
        when(mockPolicyMapper.toDTOList(policiesFound)).thenReturn(policyDTOList);

        assertEquals(policyService.findPolicy(mockAllMatchSpecification), policyDTOList);
    }

    @Test
    void testFindPolicyAnySpecificationMatch() {
        List<Policy> policiesFound = List.of(mockPolicy);
        List<PolicyDTO> policyDTOList = List.of(mockPolicyDTO);

        when(mockPolicyRepository.findAll(mockAnyMatchSpecification)).thenReturn(policiesFound);
        when(mockPolicyMapper.toDTOList(policiesFound)).thenReturn(policyDTOList);

        assertEquals(policyService.findPolicy(mockAnyMatchSpecification), policyDTOList);
    }

    @Test
    void addPolicy() {
        when(mockPolicyMapper.toEntity(mockPolicyDTO)).thenReturn(mockPolicy);
        when(mockPolicyRepository.save(mockPolicy)).thenReturn(mockSavedPolicy);
        when(mockSavedPolicy.getId()).thenReturn(SAVED_POLICY_ID);

        UUID generatedId = policyService.addPolicy(mockPolicyDTO);

        assertEquals(generatedId, SAVED_POLICY_ID);
    }

    @Test
    void updatePolicy() {
        when(mockPolicyMapper.toEntity(mockPolicyDTO)).thenReturn(mockPolicy);
        when(mockPolicyRepository.save(mockPolicy)).thenReturn(mockSavedPolicy);
        when(mockSavedPolicy.getId()).thenReturn(SAVED_POLICY_ID);

        UUID generatedId = policyService.updatePolicy(mockPolicyDTO);

        assertEquals(generatedId, SAVED_POLICY_ID);
    }
}
package com.insurance.policy_manager.unit.controller;

import com.insurance.policy_manager.controller.PolicyController;
import com.insurance.policy_manager.dto.PolicyDTO;
import com.insurance.policy_manager.repository.specification.PolicyAllMatchSpecification;
import com.insurance.policy_manager.repository.specification.PolicyAnyMatchSpecification;
import com.insurance.policy_manager.service.PolicyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PolicyControllerTest {

    private static final UUID GENERATED_POLICY_ID = UUID.randomUUID();

    @Mock
    private PolicyDTO mockPolicyDTO;
    @Mock
    private PolicyService mockPolicyService;
    @Mock
    private PolicyAllMatchSpecification mockAllMatchSpecification;
    @Mock
    private PolicyAnyMatchSpecification mockAnyMatchSpecification;

    @InjectMocks
    private PolicyController policyController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getPoliciesOnAllSpecification() {

        when(mockPolicyService.findPolicy(mockAllMatchSpecification)).thenReturn(List.of(mockPolicyDTO));

        ResponseEntity<List<PolicyDTO>> response = policyController.getPoliciesOnAllSpecification(mockAllMatchSpecification);

        assertEquals(response.getBody(), List.of(mockPolicyDTO));
    }

    @Test
    void getPoliciesOnAnySpecification() {

        when(mockPolicyService.findPolicy(mockAnyMatchSpecification)).thenReturn(List.of(mockPolicyDTO));

        ResponseEntity<List<PolicyDTO>> response = policyController.getPoliciesOnAnySpecification(mockAnyMatchSpecification);

        assertEquals(response.getBody(), List.of(mockPolicyDTO));
    }

    @Test
    void addPolicy() {
        when(mockPolicyService.addPolicy(mockPolicyDTO)).thenReturn(GENERATED_POLICY_ID);

        ResponseEntity<UUID> response = policyController.addPolicy(mockPolicyDTO);

        assertEquals(response.getBody(), GENERATED_POLICY_ID);
    }

    @Test
    void updatePolicy() {
        when(mockPolicyService.updatePolicy(mockPolicyDTO)).thenReturn(GENERATED_POLICY_ID);

        ResponseEntity<UUID> response = policyController.updatePolicy(mockPolicyDTO);

        assertEquals(response.getBody(), GENERATED_POLICY_ID);
    }
}
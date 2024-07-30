package com.insurance.policy_manager.service;

import com.insurance.policy_manager.dto.PolicyDTO;
import com.insurance.policy_manager.entity.Policy;
import com.insurance.policy_manager.mapper.PolicyMapper;
import com.insurance.policy_manager.repository.PolicyRepository;
import com.insurance.policy_manager.repository.specification.PolicyAllMatchSpecification;
import com.insurance.policy_manager.repository.specification.PolicyAnyMatchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private PolicyMapper policyMapper;

    @PreAuthorize("hasAuthority('SCOPE_read:policy')")
    public List<PolicyDTO> findPolicy(final PolicyAllMatchSpecification policySpecification){
        final List<Policy> policies = policyRepository.findAll(policySpecification);
        return policyMapper.toDTOList(policies);
    }

    @PreAuthorize("hasAuthority('SCOPE_read:policy')")
    public List<PolicyDTO> findPolicy(final PolicyAnyMatchSpecification policySpecification){
        final List<Policy> policies = policyRepository.findAll(policySpecification);
        return policyMapper.toDTOList(policies);
    }

    @PreAuthorize("hasAuthority('SCOPE_write:policy')")
    @Transactional
    public UUID addPolicy(final PolicyDTO policy){
        Policy savedPolicy = policyRepository.save(policyMapper.toEntity(policy));
        return savedPolicy.getId();
    }

    @PreAuthorize("hasAuthority('SCOPE_write:policy')")
    @Transactional
    public UUID updatePolicy(final PolicyDTO policy){
        Policy savedPolicy = policyRepository.save(policyMapper.toEntity(policy));
        return savedPolicy.getId();
    }

}

package com.insurance.policy_manager.repository.specification;

import com.insurance.policy_manager.entity.Policy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PolicyAllMatchSpecification extends PolicySpecification implements Specification<Policy> {

    public PolicyAllMatchSpecification(String policyType, BigDecimal premiumMin, BigDecimal premiumMax, Integer tenureMin, Integer tenureMax, Boolean policyIsLifetime, CoverageSpecification coverageSpecification, LiabilitySpecification liabilitySpecification) {
        super(policyType, premiumMin, premiumMax, tenureMin, tenureMax, policyIsLifetime, coverageSpecification, liabilitySpecification);
    }

    @Override
    public Predicate toPredicate(Root<Policy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(toPredicateList(root, query, criteriaBuilder));
    }
}

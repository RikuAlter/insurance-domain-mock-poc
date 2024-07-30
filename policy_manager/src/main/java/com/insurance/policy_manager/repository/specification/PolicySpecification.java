package com.insurance.policy_manager.repository.specification;

import com.insurance.policy_manager.entity.Coverage;
import com.insurance.policy_manager.entity.Liability;
import com.insurance.policy_manager.entity.Policy;
import com.insurance.policy_manager.exception.BadPolicySpecificationException;
import jakarta.persistence.criteria.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class PolicySpecification {

    private String policyType;
    private BigDecimal premiumMin;
    private BigDecimal premiumMax;
    private Integer tenureMin;
    private Integer tenureMax;
    private Boolean policyIsLifetime;
    private CoverageSpecification coverageSpecification;
    private LiabilitySpecification liabilitySpecification;

    @Getter
    @Setter
    public static class CoverageSpecification {
        private String coverageType;
        private BigDecimal coverageMin;
        private BigDecimal coverageMax;
        private BigDecimal umbrellaMin;
        private BigDecimal umbrellaMax;

        public CoverageSpecification(String coverageType, BigDecimal coverageMin, BigDecimal coverageMax, BigDecimal umbrellaMin, BigDecimal umbrellaMax) {
            this.coverageType = coverageType;
            this.coverageMin = coverageMin;
            this.coverageMax = coverageMax;
            this.umbrellaMin = umbrellaMin;
            this.umbrellaMax = umbrellaMax;
        }
    }

    @Getter
    @Setter
    public static class LiabilitySpecification {
        private String liabilityType;
        private BigDecimal exposureMin;
        private BigDecimal exposureMax;

        public LiabilitySpecification(String liabilityType, BigDecimal exposureMin, BigDecimal exposureMax) {
            this.liabilityType = liabilityType;
            this.exposureMin = exposureMin;
            this.exposureMax = exposureMax;
        }
    }

    public Predicate[] toPredicateList(Root<Policy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        verifyPolicySpecifications();
        List<Predicate> specificationList = new ArrayList<>();
        Optional.ofNullable(policyType)
                .map(type -> criteriaBuilder.like(root.get("type"), "%"+type+"%"))
                .ifPresent(specificationList::add);
        Optional.ofNullable(premiumMin)
                .map(pmin -> criteriaBuilder.ge(root.get("premium"), pmin))
                .ifPresent(specificationList::add);
        Optional.ofNullable(premiumMax)
                .map(pmax -> criteriaBuilder.le(root.get("premium"), pmax))
                .ifPresent(specificationList::add);
        Optional.ofNullable(tenureMin)
                .map(tmin -> criteriaBuilder.ge(root.get("tenure"), tmin))
                .ifPresent(specificationList::add);
        Optional.ofNullable(tenureMax)
                .map(tmax -> criteriaBuilder.le(root.get("tenure"), tmax))
                .ifPresent(specificationList::add);
        Optional.ofNullable(policyIsLifetime)
                .map(lifeTime -> criteriaBuilder.equal(root.get("policyIsLifetime"), policyIsLifetime))
                .ifPresent(specificationList::add);
        Optional.ofNullable(coverageSpecification)
                .map(cs -> coveragePredicateBuilder(cs, root, criteriaBuilder))
                .ifPresent(specificationList::addAll);
        Optional.ofNullable(liabilitySpecification)
                .map(ls -> liabilityPredicateBuilder(ls, root, criteriaBuilder))
                .ifPresent(specificationList::addAll);
        return specificationList.toArray(new Predicate[0]);
    }

    private void verifyPolicySpecifications() {
        verifyRangeSpecification(premiumMin, premiumMax, "premium amount");
        verifyRangeSpecification(tenureMin, tenureMax, "tenure");
    }

    private void verifyRangeSpecification(final Number lowerBound, final Number upperBound, final String fieldType) {
        if(null != lowerBound && null != upperBound
                && (new BigDecimal(lowerBound.toString())).compareTo(new BigDecimal(upperBound.toString())) > 0)
            throw new BadPolicySpecificationException(String.format("Bad %s range supplied.", fieldType));
    }

    private List<Predicate> liabilityPredicateBuilder(LiabilitySpecification liabilitySpecification,
                                                Root<Policy> root,
                                                CriteriaBuilder criteriaBuilder) {
        verifyLiabilitySpecification();
        Join<Policy, Liability> policyLiabilityJoin = root.join("liabilities", JoinType.INNER);

        List<Predicate> liabilityPredicates = new ArrayList<>();
        Optional.ofNullable(liabilitySpecification.liabilityType)
                .map(type -> criteriaBuilder.like(policyLiabilityJoin.get("type"), "%"+type+"%"))
                .ifPresent(liabilityPredicates::add);
        Optional.ofNullable(liabilitySpecification.exposureMin)
                .map(em -> criteriaBuilder.ge(policyLiabilityJoin.get("liabilityAmount"), em))
                .ifPresent(liabilityPredicates::add);
        Optional.ofNullable(liabilitySpecification.exposureMax)
                .map(em -> criteriaBuilder.le(policyLiabilityJoin.get("liabilityAmount"), em))
                .ifPresent(liabilityPredicates::add);
        return liabilityPredicates;
    }

    private void verifyLiabilitySpecification(){
        verifyRangeSpecification(this.liabilitySpecification.exposureMin, this.liabilitySpecification.exposureMax, "liability exposure");
    }

    private List<Predicate> coveragePredicateBuilder(CoverageSpecification coverageSpecification,
                                               Root<Policy> root,
                                               CriteriaBuilder criteriaBuilder) {
        verifyCoverageSpecification();
        Join<Policy, Coverage> policyCoverageJoin = root.join("coverages", JoinType.INNER);

        List<Predicate> coveragePredicates = new ArrayList<>();
        Optional.ofNullable(coverageSpecification.coverageType)
                .map(type -> criteriaBuilder.like(policyCoverageJoin.get("type"), "%"+type+"%"))
                .ifPresent(coveragePredicates::add);
        Optional.ofNullable(liabilitySpecification.exposureMin)
                .map(em -> criteriaBuilder.ge(policyCoverageJoin.get("coverageAmount"), em))
                .ifPresent(coveragePredicates::add);
        Optional.ofNullable(liabilitySpecification.exposureMax)
                .map(em -> criteriaBuilder.le(policyCoverageJoin.get("coverageAmount"), em))
                .ifPresent(coveragePredicates::add);
        return coveragePredicates;
    }

    private void verifyCoverageSpecification(){
        verifyRangeSpecification(this.liabilitySpecification.exposureMin, this.liabilitySpecification.exposureMax, "coverage exposure");
    }
}

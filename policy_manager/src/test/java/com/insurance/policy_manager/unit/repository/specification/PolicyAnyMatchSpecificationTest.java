package com.insurance.policy_manager.unit.repository.specification;

import com.insurance.policy_manager.entity.Policy;
import com.insurance.policy_manager.repository.specification.PolicyAnyMatchSpecification;
import com.insurance.policy_manager.repository.specification.PolicySpecification;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PolicyAnyMatchSpecificationTest {

    @Mock
    private Root<Policy> mockRoot;
    @Mock
    private Path mockPath;
    @Mock
    private Join mockJoin;
    @Mock
    private Predicate mockPredicate;
    @Mock
    private CriteriaBuilder mockCriteriaBuilder;
    @Mock
    private CriteriaQuery<Policy> mockCriteriaQuery;
    @Captor
    private ArgumentCaptor<Predicate[]> predicateListCaptor;
    @InjectMocks
    private PolicyAnyMatchSpecification policySpecification;

    @BeforeEach
    protected void setUp() {
        openMocks(this);

        setUpDummySpecifications();
        setUpRootReturns();
    }

    protected void setUpRootReturns() {
        when(mockRoot.get(any(String.class))).thenReturn(mockPath);
        when(mockRoot.join("liabilities", JoinType.INNER)).thenReturn(mockJoin);
        when(mockRoot.join("coverages", JoinType.INNER)).thenReturn(mockJoin);
        when(mockJoin.get(any(String.class))).thenReturn(mockPath);
        when(mockCriteriaBuilder.le(any(Path.class), any(Number.class))).thenReturn(mockPredicate);
        when(mockCriteriaBuilder.like(any(Path.class), any(String.class))).thenReturn(mockPredicate);
        when(mockCriteriaBuilder.ge(any(Path.class), any(Number.class))).thenReturn(mockPredicate);
        when(mockCriteriaBuilder.equal(any(Path.class), any(Boolean.class))).thenReturn(mockPredicate);
    }

    protected void setUpDummySpecifications() {
        PolicySpecification.CoverageSpecification coverageSpecification = new PolicySpecification.CoverageSpecification(
                "coverageType", BigDecimal.valueOf(900), BigDecimal.valueOf(1000), BigDecimal.valueOf(1500), BigDecimal.valueOf(2000)
        );

        PolicySpecification.LiabilitySpecification liabilitySpecification = new PolicySpecification.LiabilitySpecification(
                "liabilityType", BigDecimal.valueOf(900), BigDecimal.valueOf(1000)
        );
        policySpecification = new PolicyAnyMatchSpecification("policyType", BigDecimal.valueOf(900), BigDecimal.valueOf(1000),
                1500, 2000, true, coverageSpecification, liabilitySpecification);
    }

    @Test
    void toPredicate(){
        policySpecification.toPredicate(mockRoot, mockCriteriaQuery, mockCriteriaBuilder);
        verify(mockCriteriaBuilder).or(predicateListCaptor.capture());
        assertEquals(12, predicateListCaptor.getValue().length);
    }
}
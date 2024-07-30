package com.insurance.policy_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DynamicUpdate
@Entity
@Table(
        name = "coverage",
        schema = "policy"
)
@SequenceGenerator(schema = "policy", name = "coverage_id_gen", sequenceName = "coverage_id_gen", allocationSize = 1)
public class Coverage {

    @Id
    @Column(name = "coverage_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coverage_id_gen")
    private Long id;

    @Column(name = "coverage_type", updatable = false, nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal coverageAmount;

    @Column(nullable = true)
    private BigDecimal umbrellaAmount;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

}

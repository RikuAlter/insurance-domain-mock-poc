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
        name = "liability",
        schema = "policy"
)
@SequenceGenerator(schema = "policy", name = "liability_id_gen", sequenceName = "liability_id_gen", allocationSize = 1)
public class Liability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liability_id_gen")
    @Column(name = "liability_id")
    private Long id;

    @Column(name = "liability_type", nullable = false)
    private String type;

    @Column(name = "liability_amount")
    private BigDecimal liabilityAmount;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;
}

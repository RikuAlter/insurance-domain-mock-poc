package com.insurance.policy_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(
        name = "policy",
        schema = "policy"
)
public class Policy {

    @Id
    @Column(name = "policy_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "policy_type", nullable = false, updatable = false)
    private String type;

    @Column(name = "premium_amount", nullable = false)
    private BigDecimal premium;

    @Column(nullable = true)
    private Integer tenure;

    @CreationTimestamp
    private LocalDate publishedDate;

    @UpdateTimestamp
    private LocalDate lastUpdateDate;

    @Column(name = "underwriter_id", nullable = false, updatable = false)
    private String underwriterId;

    @Column(name = "is_lifetime")
    private Boolean policyIsLifetime;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "policy")
    private List<Coverage> coverages;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "policy")
    private List<Liability> liabilities;

    @PrePersist
    @PreUpdate
    void prePersist(){
        Optional.ofNullable(this.getCoverages())
                .ifPresent(coverages ->
                        coverages.forEach(coverage -> coverage.setPolicy(this)));
        Optional.ofNullable(this.getLiabilities())
                .ifPresent(liabilities ->
                        liabilities.forEach(liability -> liability.setPolicy(this)));
    }
}

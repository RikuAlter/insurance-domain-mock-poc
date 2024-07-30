package com.insurance.policy_manager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.insurance.policy_manager.views.PolicyViews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
@EqualsAndHashCode
@JsonInclude(NON_EMPTY)
public class PolicyDTO {

    @JsonView(PolicyViews.OrganizationView.class)
    private String policyId;
    @JsonView(PolicyViews.PublicView.class)
    private String policyType;
    @JsonView(PolicyViews.PublicView.class)
    private BigDecimal premiumAmount;
    @JsonView(PolicyViews.PublicView.class)
    private Integer policyTenure;
    @JsonView(PolicyViews.PublicView.class)
    private Boolean policyIsLifetime;
    @JsonView(PolicyViews.PublicView.class)
    private String policyPublishedDate;
    @JsonView(PolicyViews.PublicView.class)
    private String policyUpdateDate;
    @JsonView(PolicyViews.OrganizationView.class)
    private String underwriterId;
    @JsonView(PolicyViews.PublicView.class)
    private List<CoverageDTO> coverages;
    @JsonView(PolicyViews.PublicView.class)
    private List<LiabilityDTO> liabilities;
}

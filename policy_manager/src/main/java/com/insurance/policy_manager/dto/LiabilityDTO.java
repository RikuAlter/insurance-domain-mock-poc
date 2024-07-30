package com.insurance.policy_manager.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.insurance.policy_manager.views.PolicyViews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema
@EqualsAndHashCode
public class LiabilityDTO {
    @JsonView(PolicyViews.OrganizationView.class)
    private String liabilityId;
    @JsonView(PolicyViews.PublicView.class)
    private String liabilityType;
    @JsonView(PolicyViews.PublicView.class)
    private BigDecimal exposure;
}

package com.insurance.policy_manager.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.insurance.policy_manager.views.PolicyViews;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema
@EqualsAndHashCode
public class CoverageDTO {
    @JsonView(PolicyViews.PublicView.class)
    private Long coverageId;
    @JsonView(PolicyViews.PublicView.class)
    private String coverageType;
    @JsonView(PolicyViews.PublicView.class)
    private BigDecimal exposure;
    @JsonView(PolicyViews.PublicView.class)
    private BigDecimal umbrellaCoverage;
}

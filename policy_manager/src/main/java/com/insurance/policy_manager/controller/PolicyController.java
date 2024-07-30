package com.insurance.policy_manager.controller;

import com.insurance.policy_manager.dto.PolicyDTO;
import com.insurance.policy_manager.repository.specification.PolicyAllMatchSpecification;
import com.insurance.policy_manager.repository.specification.PolicyAnyMatchSpecification;
import com.insurance.policy_manager.service.PolicyService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("policy")
public class PolicyController {

    @Autowired
    private PolicyService policyLookupService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PolicyDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            })
    })
    @GetMapping("/fetch/all")
    public ResponseEntity<List<PolicyDTO>> getPoliciesOnAllSpecification(@Parameter(name = "Policy Lookup",
            description = "POJO containing the lookup specifications for policies, all specifications must match")
                                                           @RequestBody PolicyAllMatchSpecification policySpecification){
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(policyLookupService.findPolicy(policySpecification));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PolicyDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            })
    })
    @GetMapping("/fetch/any")
    public ResponseEntity<List<PolicyDTO>> getPoliciesOnAnySpecification(@Parameter(name = "Policy Lookup",
            description = "POJO containing the lookup specifications for policies, any specifications may match")
                                                            @RequestBody PolicyAnyMatchSpecification policySpecification){
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(policyLookupService.findPolicy(policySpecification));
    }

    @PostMapping("/addpolicy")
    public ResponseEntity<UUID> addPolicy(@RequestBody PolicyDTO policy) {
        return ResponseEntity.ok()
                .body(policyLookupService.addPolicy(policy));
    }

    @PatchMapping("/updatepolicy")
    public ResponseEntity<UUID> updatePolicy(@RequestBody PolicyDTO policy) {
        return ResponseEntity.ok()
                .body(policyLookupService.updatePolicy(policy));
    }

}

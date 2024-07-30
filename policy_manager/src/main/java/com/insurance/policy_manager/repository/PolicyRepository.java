package com.insurance.policy_manager.repository;

import com.insurance.policy_manager.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, String>, JpaSpecificationExecutor<Policy> {

}

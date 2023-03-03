package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.plan.PlanApiIntegrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanAPIIntegrationInfoRepository extends JpaRepository<PlanApiIntegrationInfo, Long> {
}
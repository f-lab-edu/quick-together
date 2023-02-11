package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.plan.PlanApiClient;
import com.flab.quicktogether.timeplan.domain.plan.PlanJpaRepository;
import com.flab.quicktogether.timeplan.domain.exception.DuplicatePlanException;
import com.flab.quicktogether.timeplan.domain.exception.NotFoundPlanException;
import com.flab.quicktogether.timeplan.presentation.dto.PlanUpdateRequestDto;
import com.flab.quicktogether.timeplan.presentation.dto.PlanCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 추후 Calendar API 적용예정으로 현재는 JPA만 지원하는 상태임.
 */
@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanJpaRepository planJpaRepository;
    private final Map<String, PlanApiClient> planApiClients;
    private final PlanAPIIntegrationInfoRepository planAPIIntegrationInfoRepository;
    public void registerPlan(Long memberId, PlanCreateRequestDto planDto) {
        Plan plan = planDto.toEntity(memberId);
        verifyDuplicated(plan);
        planJpaRepository.save(plan);
    }

    public void modifyPlan(Long memberId, Long planId, PlanUpdateRequestDto planUpdateRequestDto) {
        Plan planForUpdate = planUpdateRequestDto.toEntityOf(memberId);
        findPlan(memberId, planId)
                .update(planForUpdate);
    }

    public void removePlan(Long memberId, Long planId) {
        findPlan(memberId, planId)
                .cancel();
    }

    private Plan findPlan(Long memberId, Long planId) {
        return planJpaRepository.findByIdAndMemberId(planId, memberId)
                .orElseThrow(NotFoundPlanException::new);
    }

    private void verifyDuplicated(Plan plan) {
        if (planJpaRepository.existsByPlanNameAndTimeBlock(plan.getPlanName(), plan.getTimeBlock())) {
            throw new DuplicatePlanException();
        }
    }
}

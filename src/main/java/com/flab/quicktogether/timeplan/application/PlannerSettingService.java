package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSettingRepository;
import com.flab.quicktogether.timeplan.presentation.dto.PlannerSettingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PlannerSettingService {

    private final PlannerSettingRepository plannerSettingRepository;

    public PlannerSettingService(PlannerSettingRepository plannerSettingRepository) {
        this.plannerSettingRepository = plannerSettingRepository;
    }

    public Long regist(Long loginMemberId, PlannerSettingRequest plannerSettingRequest) {
        PlannerSetting plannerSetting = plannerSettingRequest.toEntity(loginMemberId);
        return plannerSettingRepository.save(plannerSetting).getId();
    }
}

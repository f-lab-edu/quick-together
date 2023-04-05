package com.flab.quicktogether.timeplan.application;

import com.flab.quicktogether.timeplan.domain.exception.NotFoundPlannerSettingException;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSetting;
import com.flab.quicktogether.timeplan.domain.setting.PlannerSettingRepository;
import com.flab.quicktogether.timeplan.presentation.dto.PlannerSettingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PlannerSettingService {

    private final PlannerSettingRepository repository;

    public PlannerSettingService(PlannerSettingRepository repository) {
        this.repository = repository;
    }

    public Long regist(Long loginMemberId, PlannerSettingDto plannerSettingDto) {
        PlannerSetting plannerSetting = plannerSettingDto.toEntity(loginMemberId);
        Optional<PlannerSetting> existingSetting = repository.findScheduleSettingByMemberId(loginMemberId);

        if (existingSetting.isPresent()) {
           return existingSetting
                    .get()
                    .update(plannerSetting);
        } else {
            repository.save(plannerSetting);
            return plannerSetting.getId();
        }
    }

    public PlannerSettingDto show(Long loginMemberId) {
        PlannerSetting setting = repository.findScheduleSettingByMemberId(loginMemberId)
                .orElseThrow(NotFoundPlannerSettingException::new);
        return PlannerSettingDto.from(setting);

    }
}

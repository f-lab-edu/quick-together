package com.flab.quicktogether.timeplan.domain.setting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlannerSettingRepository extends JpaRepository<PlannerSetting, Long> {

    Optional<PlannerSetting> findScheduleSettingByMemberId(Long memberId);
}

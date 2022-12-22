package com.flab.quicktogether.timeplan.infrastructure;

import com.flab.quicktogether.timeplan.domain.TimePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimePlanRepository extends JpaRepository<TimePlan, Long> {

    public Optional<TimePlan> findByMemberId(Long memberId);

}
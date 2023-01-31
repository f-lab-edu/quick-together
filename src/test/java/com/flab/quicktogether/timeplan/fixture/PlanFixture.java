package com.flab.quicktogether.timeplan.fixture;

import com.flab.quicktogether.timeplan.domain.plan.Plan;
import com.flab.quicktogether.timeplan.domain.value_type.TimeBlock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlanFixture {
    public static final LocalDate FIXED_DATE = LocalDate.now().plusDays(1L);
    public static final LocalDateTime LIMIT_DATE = LocalDate.now().plusDays(2L).atStartOfDay();
    public static final LocalDate OVER_LIMIT_DATE = LocalDate.now().plusDays(3L);

    public static final LocalDate OUT_DATE = LocalDate.now().minusDays(1L);


    public static Plan newPlanIgnoreEx(Long memberId, String planName, LocalDate target, String startTimeAndEndTime) throws Exception {

        Constructor<Plan> declaredConstructor = Plan.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Plan plan = declaredConstructor.newInstance();

        setField(plan,"memberId", memberId);
        setField(plan,"planName", planName);
        setField(plan,"timeBlock", newTimeBlockByString(target, startTimeAndEndTime));

        return plan;
    }

    private static TimeBlock newTimeBlockByString(LocalDate target, String fromTo) {
        String[] split = fromTo.split("~");
        LocalTime startTime = LocalTime.parse(split[0]);
        LocalTime endTime = LocalTime.parse(split[1]);

        LocalDateTime startDateTime = target.atTime(startTime);

        LocalDateTime endDateTime = target.atTime(endTime);

        if (endTime.equals(LocalTime.MIN)) {
            endDateTime = endDateTime.plusDays(1L);
        }

        return TimeBlock.of(startDateTime, endDateTime);
    }

    private static void setField(Plan plan, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = plan.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(plan, value);
    }

    public static String randomName() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}

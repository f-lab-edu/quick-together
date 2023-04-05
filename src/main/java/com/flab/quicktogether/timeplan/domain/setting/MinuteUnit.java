package com.flab.quicktogether.timeplan.domain.setting;

import lombok.Getter;

@Getter
public enum MinuteUnit {
    FIVE(5),TEN(10),QUARTER(15),HALF(30),HOUR(60);

    private final int unitValue;

    MinuteUnit(int unitValue) {
        this.unitValue = unitValue;
    }

    public static MinuteUnit valueOf(int unitValue) {
        for (MinuteUnit minuteUnit : MinuteUnit.values()) {
            if (minuteUnit.getUnitValue() == unitValue) {
                return minuteUnit;
            }
        }
        throw new IllegalArgumentException("해당하는 분 단위가 없습니다.");
    }
    public int getUnitValue() {
        return this.unitValue;
    }


}

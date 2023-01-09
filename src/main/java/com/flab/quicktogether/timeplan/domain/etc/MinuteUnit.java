package com.flab.quicktogether.timeplan.domain.etc;

import lombok.Getter;

@Getter
public enum MinuteUnit {
    FIVE(5),TEN(10),QUARTER(15),HALF(30),HOUR(60);

    private final int unitValue;

    MinuteUnit(int unitValue) {
        this.unitValue = unitValue;
    }

    public int getUnitValue() {
        return this.unitValue;
    }


}

package com.vertical.annotation.autolayout;

/**
 * Created by user on 2017/8/22.
 */

public enum AutoLayoutEnum {

    GONE(0), VISIBLE(1);

    private int value;

    AutoLayoutEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

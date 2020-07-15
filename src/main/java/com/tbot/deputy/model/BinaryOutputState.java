package com.tbot.deputy.model;

public enum BinaryOutputState {
    ON,
    OFF
    ;
    
    public BinaryOutputState toggle() {
        if (this == ON)
            return OFF;
        else
            return ON;
    }
}

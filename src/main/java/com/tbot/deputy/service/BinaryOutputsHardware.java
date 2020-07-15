package com.tbot.deputy.service;

import com.tbot.deputy.model.BinaryOutputState;

public interface BinaryOutputsHardware {
    public int getNumberOfOutputs();
    public BinaryOutputState readOutputState(int binOutNumber);
    public void setOutputState(int binOutNumber, BinaryOutputState state);
}

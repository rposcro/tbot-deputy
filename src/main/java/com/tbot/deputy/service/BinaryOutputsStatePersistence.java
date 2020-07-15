package com.tbot.deputy.service;

import com.tbot.deputy.model.BinaryOutputState;

public interface BinaryOutputsStatePersistence {
    public void persistOutputState(int outputNumber, BinaryOutputState pinState);
    public BinaryOutputState fetchBinaryOutputsState(int outputNumber);
}

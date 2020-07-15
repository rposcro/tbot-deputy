package com.tbot.deputy.service;

import java.util.List;

import com.tbot.deputy.model.BinaryOutput;
import com.tbot.deputy.model.BinaryOutputState;

public interface BinaryOutputsService {
    public List<BinaryOutput> readOutputsStates();
    public BinaryOutput readOutputState(int binOutNumber);
    public void changeOutputState(int binOutNumber, BinaryOutputState change);
    public void toggleOutputState(int binOutNumber);
}

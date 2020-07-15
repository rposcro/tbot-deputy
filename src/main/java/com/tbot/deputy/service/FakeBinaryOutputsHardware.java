package com.tbot.deputy.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.tbot.deputy.model.BinaryOutputState;

@Component
@Profile("development")
public class FakeBinaryOutputsHardware implements BinaryOutputsHardware {

    private static final int NUMBER_OF_OUTPUTS = 1;
    private static Log LOG = LogFactory.getLog(FakeBinaryOutputsHardware.class);
    
    private BinaryOutputState[] digoutsStates;
    
    public FakeBinaryOutputsHardware() {
        this.digoutsStates = new BinaryOutputState[1];
        digoutsStates[0] = BinaryOutputState.OFF;
    }
    
    @Override
    public int getNumberOfOutputs() {
        return NUMBER_OF_OUTPUTS;
    }
    
    @Override
    public BinaryOutputState readOutputState(int digoutNumber) {
        return digoutsStates[digoutNumber];
    }

    @Override
    public void setOutputState(int digoutNumber, BinaryOutputState state) {
        digoutsStates[digoutNumber] = state;
        LOG.debug("Digout " + digoutNumber + " state changed to " + state);
    }
}

package com.tbot.deputy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbot.deputy.model.BinaryOutput;
import com.tbot.deputy.model.BinaryOutputState;

@Service
public class BinaryOutputsServiceImpl implements BinaryOutputsService {

    private static Log LOG = LogFactory.getLog(BinaryOutputsServiceImpl.class);
    
    private BinaryOutputsHardware digoutHardware;
    private BinaryOutputsStatePersistence digoutPersistence;
    
    @Autowired
    public BinaryOutputsServiceImpl(
            BinaryOutputsHardware digoutHardware,
            BinaryOutputsStatePersistence digoutPersistence) {
        this.digoutHardware = digoutHardware;
        this.digoutPersistence = digoutPersistence;
    }
    
    @Override
    public void changeOutputState(int binOutNumber, BinaryOutputState state) {
        LOG.debug("Requested state change for binary output: " + binOutNumber + ", new state: " + state);
        digoutHardware.setOutputState(binOutNumber, state);
        digoutPersistence.persistOutputState(binOutNumber, state);
    }

    @Override
    public void toggleOutputState(int binOutNumber) {
        LOG.debug("Requested state toggle for binary output: " + binOutNumber);
        BinaryOutputState newState = digoutHardware.readOutputState(binOutNumber).toggle();
        digoutHardware.setOutputState(binOutNumber, newState);
        digoutPersistence.persistOutputState(binOutNumber, newState);
    }

    @Override
    public BinaryOutput readOutputState(int binOutNumber) {
        BinaryOutput output = new BinaryOutput(
        		binOutNumber, digoutHardware.readOutputState(binOutNumber));
        return output;
    }

	@Override
	public List<BinaryOutput> readOutputsStates() {
		int num = digoutHardware.getNumberOfOutputs();
		List<BinaryOutput> outputs = new ArrayList<>(num);
		for (int i = 0; i < num; i++) {
			outputs.add(
				new BinaryOutput(
	        		i, digoutHardware.readOutputState(i)));
		}
		return outputs;
	}
}

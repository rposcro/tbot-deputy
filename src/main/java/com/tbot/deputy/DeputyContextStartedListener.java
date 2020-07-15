package com.tbot.deputy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.tbot.deputy.model.BinaryOutputState;
import com.tbot.deputy.service.BinaryOutputsHardware;
import com.tbot.deputy.service.BinaryOutputsStatePersistence;

public class DeputyContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Log LOG = LogFactory.getLog(DeputyContextStartedListener.class);
    
    private static final BinaryOutputState DEF_DIGOUT_STATE = BinaryOutputState.OFF;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Listener for context started called");
        
        ApplicationContext context = event.getApplicationContext();
        BinaryOutputsHardware digoutHardware = context.getBean(BinaryOutputsHardware.class);
        BinaryOutputsStatePersistence digoutPersistence = context.getBean(BinaryOutputsStatePersistence.class);
        int numberOfOutputs = digoutHardware.getNumberOfOutputs();
        LOG.info("Number od digitial outputs for active hardware is " + numberOfOutputs);
        
        for (int i = 0; i < numberOfOutputs; i++) {
            BinaryOutputState digoutState = digoutPersistence.fetchBinaryOutputsState(i);
            LOG.info("Obtained last known state for digout " + i + " as " + digoutState + ", attempt to initialize it");
            digoutHardware.setOutputState(i, digoutState == null ? DEF_DIGOUT_STATE : digoutState);
        }
    }
}

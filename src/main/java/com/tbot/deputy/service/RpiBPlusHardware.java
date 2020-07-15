package com.tbot.deputy.service;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.tbot.deputy.model.BinaryOutputState;

@Component
@Profile("prod-rpibplus")
public class RpiBPlusHardware implements BinaryOutputsHardware {

	private static final Log LOG = LogFactory.getLog(RpiBPlusHardware.class);
    
    private final int numberOfPins;
    private final Pin[] outputPinout;
    
    private GpioController gpioController;
    private GpioPinDigitalOutput[] outPins;
    private BinaryOutputState[] binOutStates;
    
    @Autowired
    public RpiBPlusHardware(@Value("${hardware.rpibplus.output_pins}") String outputPins) {
        this.outputPinout = parsePinout(outputPins);
        this.numberOfPins = outputPinout.length;
        this.gpioController = GpioFactory.getInstance();
        this.outPins = new GpioPinDigitalOutput[numberOfPins];
        this.binOutStates = new BinaryOutputState[numberOfPins];
        
        for (int i = 0; i < numberOfPins; i++) {
            outPins[i] = gpioController.provisionDigitalOutputPin(
                    outputPinout[i], "Pin " + i, com.pi4j.io.gpio.PinState.LOW);
            binOutStates[i] = BinaryOutputState.OFF;
            LOG.info("Configured logical binary output " + i + " assigned to pin " + outputPinout[i] + " with default value OFF");
        }
    }
    
    RpiBPlusHardware() {
    	this.numberOfPins = 0;
    	this.outputPinout = new Pin[0];
    	// just for testing purpose
    }
    
    Pin[] parsePinout(String encoded) {
    	StringTokenizer tokenizer = new StringTokenizer(encoded, ",");
    	int tokenNum = tokenizer.countTokens();
    	Pin[] pinout = new Pin[tokenNum];
    	int idx = 0;
    	
    	while (tokenizer.hasMoreTokens()) {
    		String gpioIdStr = tokenizer.nextToken().trim();
    		pinout[idx++] = RaspiPin.getPinByAddress(Integer.valueOf(gpioIdStr));
    	}
    	
    	return pinout;
    }
    
    @Override
    public int getNumberOfOutputs() {
        return numberOfPins;
    }
    
    @Override
    public BinaryOutputState readOutputState(int binOutNumber) {
        BinaryOutputState state = binOutStates[binOutNumber]; 
        LOG.debug("Read binary output " + binOutNumber + " as " + state);
        return state;
    }

    @Override
    public void setOutputState(int binOutNumber, BinaryOutputState state) {
        if (BinaryOutputState.ON == state) {
            outPins[binOutNumber].high();
        }
        else if (BinaryOutputState.OFF == state) {
            outPins[binOutNumber].low();
        }
        binOutStates[binOutNumber] = state;
        LOG.info("Changed value of binary output " + binOutNumber + " to " + state);
    }
}

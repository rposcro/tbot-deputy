package com.tbot.deputy.model;

public class BinaryOutput {
	
	private int outputNumber;
	private BinaryOutputState state;

	public BinaryOutput(int outputNumber, BinaryOutputState state) {
		this.outputNumber = outputNumber;
		this.state = state;
	}

	public int getOutputNumber() {
		return outputNumber;
	}
	
	public BinaryOutputState getState() {
		return state;
	}
}

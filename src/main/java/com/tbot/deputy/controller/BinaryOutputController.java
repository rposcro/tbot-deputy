package com.tbot.deputy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tbot.deputy.model.BinaryOutput;
import com.tbot.deputy.model.BinaryOutputState;
import com.tbot.deputy.service.BinaryOutputsService;

@RestController
@RequestMapping(value = ControllerConstants.ENDPOINT_BINARY_OUTPUTS)
public class BinaryOutputController extends AbstractController {
    
    private static final String STATE_TOGGLE = "toggle";
    private static final String STATE_ON = "on";
    private static final String STATE_OFF = "off";
    
    private BinaryOutputsService outputService;
    
    @Autowired
    public BinaryOutputController(BinaryOutputsService outputService) {
        this.outputService = outputService;
    }
    
    @RequestMapping(value = "/{binOutNumber}", method = RequestMethod.GET, produces = MEDIA_TYPE)
    public BinaryOutput readOutputValue(
            @PathVariable(value = "binOutNumber") Integer binOutNumber
            ) {
        BinaryOutput output = outputService.readOutputState(binOutNumber);
        return output;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MEDIA_TYPE)
    public List<BinaryOutput> readAllOutputsValues() {
        List<BinaryOutput> outputs = outputService.readOutputsStates();
    	return outputs;
    }

    
    @RequestMapping(value = "/{binOutNumber}", method = RequestMethod.PATCH, produces = MEDIA_TYPE)
    public ResponseEntity<String> changeOutputValue(
            @PathVariable(value = "binOutNumber") Integer binOutNumber,
            @RequestParam(value = "state", required = true) String stateRef
            ) {

        if (STATE_TOGGLE.equals(stateRef)) {
            outputService.toggleOutputState(binOutNumber);
        }
        else if (STATE_ON.equals(stateRef)) {
            outputService.changeOutputState(binOutNumber, BinaryOutputState.ON);
        }
        else if (STATE_OFF.equals(stateRef)) {
            outputService.changeOutputState(binOutNumber, BinaryOutputState.OFF);
        }
        else {
            return new ResponseEntity<String>(
                    String.format("Unrecognized binary output state request!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}

package com.tbot.deputy.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.tbot.deputy.model.BinaryOutputState;

@Component
@Profile("linux")
public class LinuxFileBinaryOutputsStatePersistence implements BinaryOutputsStatePersistence {

    private static final Log LOG = LogFactory.getLog(LinuxFileBinaryOutputsStatePersistence.class);
    private final String dataPath;
    
    @Autowired
    public LinuxFileBinaryOutputsStatePersistence(
            @Value("${application.path.data}") String dataPath) {
        this.dataPath = dataPath;
    }
    
    @Override
    public void persistOutputState(int binOutNumber, BinaryOutputState digoutState) {
        String filePath = getDigoutFilePath(binOutNumber);
        File file = new File(filePath);
        file.delete();
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(file);
            writer.write(digoutState.toString());
            writer.write("\n");
        }
        catch(IOException e) {
            LOG.error("Failed to persist binary output state!", e);
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    @Override
    public BinaryOutputState fetchBinaryOutputsState(int binOutNumber) {
        String filePath = getDigoutFilePath(binOutNumber);
        File file = new File(filePath);
        
        if (!file.exists())
            return null;
        
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String stateStr = reader.readLine();
            if (stateStr != null)
                return BinaryOutputState.valueOf(stateStr.trim());
        }
        catch(IOException e) {
            LOG.error("Failed to fetch binary output state from file!", e);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                }
            }
        }
        return null;
    }

    private void checkDirOrCreate(String path) {
        File dir = new File(dataPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    String getDigoutFilePath(int pinNumber) {
        checkDirOrCreate(dataPath);
        if (pinNumber < 10)
            return dataPath + "/binout0" + pinNumber;
        else
            return dataPath + "/binout" + pinNumber;
    }
}

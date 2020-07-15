package com.tbot.deputy.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinuxFileBinaryOutputsStatePersistenceTest {

    @Test
    public void testGetPinFilePath() {
        LinuxFileBinaryOutputsStatePersistence persistence = new LinuxFileBinaryOutputsStatePersistence("/file/path");
        String filePath = persistence.getDigoutFilePath(2);
        assertEquals("/file/path/binout02", filePath);
        filePath = persistence.getDigoutFilePath(0);
        assertEquals("/file/path/binout00", filePath);
        filePath = persistence.getDigoutFilePath(9);
        assertEquals("/file/path/binout09", filePath);
        filePath = persistence.getDigoutFilePath(23);
        assertEquals("/file/path/binout23", filePath);
        filePath = persistence.getDigoutFilePath(1223);
        assertEquals("/file/path/binout1223", filePath);
    }

}

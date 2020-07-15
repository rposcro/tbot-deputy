package com.tbot.deputy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController extends AbstractController {
    
    public static final String NOT_AVAILABLE = "Not Available";
    
    private ApplicationInfo appInfo;
    
    @Autowired
    public AboutController(@Value("${spring.application.name}") String appName) {
        final String appVersion = retrieveAppVersion();
        this.appInfo = new ApplicationInfo(
                appName != null ? appName : NOT_AVAILABLE,
                appVersion != null ? appVersion : NOT_AVAILABLE);
    }
    
    @RequestMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> home(HttpServletRequest request) {
        return new ResponseEntity<String>(
                String.format("Welcome.\nApplication name: %s\nVersion: %s\nYour IP: %s\n", 
                              appInfo.getAppName(), 
                              appInfo.getAppVersion(), 
                              "" + request.getRemoteAddr()),
                HttpStatus.OK);
    }
    
    String retrieveAppVersion() {
        return AboutController.class.getPackage().getImplementationVersion();
    }
    
    public static class ApplicationInfo {
        private String appName;
        private String appVersion;
        
        public ApplicationInfo(String appName, String appVersion) {
            this.appName = appName;
            this.appVersion = appVersion;
        }
        
        public String getAppName() {
            return appName;
        }
        
        public String getAppVersion() {
            return appVersion;
        }
    }
}

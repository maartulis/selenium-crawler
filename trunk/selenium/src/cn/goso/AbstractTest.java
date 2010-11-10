package cn.goso; 

import java.io.File; 

import org.openqa.selenium.server.RemoteControlConfiguration; 
import org.openqa.selenium.server.SeleniumServer; 
//import org.testng.annotations.AfterClass; 
//import org.testng.annotations.BeforeClass; 

import com.thoughtworks.selenium.DefaultSelenium; 
import com.thoughtworks.selenium.Selenium; 

public abstract class AbstractTest { 
    protected Selenium selenium; 
    protected static SeleniumServer seleniumServer; 
    String browser = "*chrome"; 
    String url = "http://www.google.com"; 

    public void setUp() throws Exception { 
        startSeleniumServer(); 
        selenium = new DefaultSelenium("localhost", 4444, browser, url); 
        selenium.start(); 
    } 

    private void startSeleniumServer() throws Exception { 
        String template = "C:/workspace/selenium-templates"; 
        RemoteControlConfiguration rcConf = new RemoteControlConfiguration(); 
        rcConf.setPort(4444); 
        rcConf.setReuseBrowserSessions(true); 
        rcConf.setBrowserSideLogEnabled(true); 
        rcConf.setSingleWindow(true); 
        rcConf.setFirefoxProfileTemplate(new File(template)); 
        seleniumServer = new SeleniumServer(rcConf); 
        seleniumServer.start(); 

    } 
    
    protected void stop() { 
        selenium.stop(); 
        seleniumServer.stop(); 
    } 

}

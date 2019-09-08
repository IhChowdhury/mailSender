package com.ibrahim.mailsender.core;

import java.io.IOException;
import java.sql.Driver;
import java.util.HashMap;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class AppLauncher {

    private ChromeDriver chromeDriver = null;
    private static AppLauncher INSTANCE = null;

    private AppLauncher() {
    }

    public static AppLauncher getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AppLauncher();
        }

        return INSTANCE;
    }

    public ChromeDriver getChromeDriver() {
        if (chromeDriver == null) {
            initDriver();
        }
        return chromeDriver;
    }

    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "lib/chromeDriver.exe");
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-javascript");
        options.addArguments("--disable-infobars");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_setting_values.javascript", 2);
        options.setExperimentalOption("prefs", chromePrefs);
        chromeDriver = new ChromeDriver(options);
    }

    public void cleanChromeDriver() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver* /T");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeChormeDriver(){
        chromeDriver.quit();
        chromeDriver = null;
    }

}

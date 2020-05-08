package com.mightypetra.testautomation;

import com.codeborne.selenide.Configuration;
import org.slf4j.Logger;

public class DriverUtils {

    public final static long TIMEOUT = 60000;
    public final static String CHROME = "chrome";
    public final static String FIREFOX = "firefox";

    public static void setDriver(String browser) {
        Configuration.browser = browser;
        Configuration.startMaximized = true;
        Configuration.timeout = TIMEOUT;
        Configuration.reportsFolder = "allure-results";
    }
}

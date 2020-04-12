package com.mightypetra.testautomation;

import com.codeborne.selenide.Configuration;

public class DriverUtils {

    public final static long TIMEOUT = 60000;
    public final static String CHROME = "chrome";

    public static void setDriver(String browser) {
        Configuration.browser = browser;
        Configuration.startMaximized = true;
        Configuration.timeout = TIMEOUT;
        Configuration.reportsFolder = "allure-results";
    }
}

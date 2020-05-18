package com.mightypetra.testautomation;

import java.util.UUID;

import static java.lang.Double.parseDouble;

public class Utils {

    public static String generateString() {
        return UUID.randomUUID().toString().replaceAll("[\\d-]","");
    }

    public static double priceToDouble(String val){
        return parseDouble(val.replaceAll("[^\\d.]+", ""));
    }
}

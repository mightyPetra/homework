package com.mightypetra.testautomation;

import static java.lang.Double.parseDouble;

public class ShopItem {

    private double priceToDouble(String val){
        return parseDouble(val.replaceAll("[^\\d.]+", ""));
    }
}

package com.gebarowski.endomondoupdater.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    private Utils() {
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}

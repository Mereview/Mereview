package com.ssafy.mereview.common.util;

import java.util.Map;

public class ExperienceConstants {

    public static final int BRONZE_MAX_EXP = 100;
    public static final int SILVER_MAX_EXP = 200;
    public static final int GOLD_MAX_EXP = 500;
    public static final int PLATINUM_MAX_EXP = 1000;
    public static final int DIAMOND_MAX_EXP = 10000;

    public static final Map<String, Integer> TIER_MAX_EXP = Map.of(
            "BRONZE", BRONZE_MAX_EXP,
            "SILVER", SILVER_MAX_EXP,
            "GOLD", GOLD_MAX_EXP,
            "PLATINUM", PLATINUM_MAX_EXP,
            "DIAMOND", DIAMOND_MAX_EXP
    );
}

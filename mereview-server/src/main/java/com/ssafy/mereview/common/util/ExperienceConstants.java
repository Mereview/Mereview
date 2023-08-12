package com.ssafy.mereview.common.util;

import com.ssafy.mereview.domain.member.entity.Rank;

import java.util.Map;

import static com.ssafy.mereview.domain.member.entity.Rank.*;

public class ExperienceConstants {

    public static final int BRONZE_MAX_EXP = 100;
    public static final int SILVER_MAX_EXP = 200;
    public static final int GOLD_MAX_EXP = 500;
    public static final int PLATINUM_MAX_EXP = 1000;
    public static final int DIAMOND_MAX_EXP = 10000;

    public static final Map<Rank, Integer> TIER_MAX_EXP_MAP = Map.of(
            NONE, 0,
            BRONZE, BRONZE_MAX_EXP,
            SILVER, SILVER_MAX_EXP,
            GOLD, GOLD_MAX_EXP,
            PLATINUM, PLATINUM_MAX_EXP,
            DIAMOND, DIAMOND_MAX_EXP
    );
}

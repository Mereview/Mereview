package com.ssafy.mereview.common.util;

import com.ssafy.mereview.domain.member.entity.Rank;

import java.util.Map;

import static com.ssafy.mereview.domain.member.entity.Rank.*;

public class SizeConstants {
    public static final int PAGE_SIZE = 100;
    public static final int MOVIE_SIZE = 50;

    public static final int REVIEW_NONE_MAX_COUNT = 1;
    public static final int REVIEW_BRONZE_MAX_COUNT = 50;
    public static final int REVIEW_SILVER_MAX_COUNT = 100;
    public static final int REVIEW_GOLD_MAX_COUNT = 300;
    public static final int REVIEW_PLATINUM_MAX_COUNT = 800;
    public static final int REVIEW_DIAMOND_MAX_COUNT = 10000;

    public static final Map<Rank, Integer> REVIEW_ACHIEVEMENT_MAX_COUNT_MAP = Map.of(
            NONE, 1,
            BRONZE, REVIEW_BRONZE_MAX_COUNT,
            SILVER, REVIEW_SILVER_MAX_COUNT,
            GOLD, REVIEW_GOLD_MAX_COUNT,
            PLATINUM, REVIEW_PLATINUM_MAX_COUNT,
            DIAMOND, REVIEW_DIAMOND_MAX_COUNT
    );

    public static final int COMMENT_NONE_MAX_COUNT = 1;
    public static final int COMMENT_BRONZE_MAX_COUNT = 50;
    public static final int COMMENT_SILVER_MAX_COUNT = 100;
    public static final int COMMENT_GOLD_MAX_COUNT = 300;
    public static final int COMMENT_PLATINUM_MAX_COUNT = 800;
    public static final int COMMENT_DIAMOND_MAX_COUNT = 10000;

    public static final Map<Rank, Integer> COMMENT_ACHIEVEMENT_MAX_COUNT_MAP = Map.of(
            NONE, COMMENT_NONE_MAX_COUNT,
            BRONZE, COMMENT_BRONZE_MAX_COUNT,
            SILVER, COMMENT_SILVER_MAX_COUNT,
            GOLD, COMMENT_GOLD_MAX_COUNT,
            PLATINUM, COMMENT_PLATINUM_MAX_COUNT,
            DIAMOND, COMMENT_DIAMOND_MAX_COUNT
    );

}

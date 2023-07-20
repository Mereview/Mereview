package com.ssafy.mereview.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultPage<T> {
    private T data;
    private int pageNumber;
    private int pageSize;
    private int pageTotalCnt;
}

package com.ssafy.mereview.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class ResultPageResponse<T> {
    private T data;
    private int pageNumber;
    private int pageSize;
    private int pageTotalCnt;

    @Builder
    public ResultPageResponse(T data, int pageNumber, int pageSize, int pageTotalCnt) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageTotalCnt = pageTotalCnt;
    }
}

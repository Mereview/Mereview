package com.ssafy.mereview.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponse<T> {
    private T data;
    private int pageNumber;
    private int pageSize;
    private int pageTotalCnt;

    @Builder
    public PageResponse(T data, int pageNumber, int pageSize, int pageTotalCnt) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pageTotalCnt = pageTotalCnt;
    }
}

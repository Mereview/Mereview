package com.ssafy.mereview.common.util;

import com.ssafy.mereview.common.exception.entity.CustomException;
import com.ssafy.mereview.common.exception.entity.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
public class FileExtFilter {
    private final String[] IMG_EXTENSION = {"png", "jpg", "jpeg", "gif"};
    private final String[] BAD_EXTENSION = {"jsp", "php", "asp", "html", "perl"};

    public void imageFilter(List<MultipartFile> multipartFiles) {
        boolean isValid = false;
        for (MultipartFile file : multipartFiles) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String ext = extractExtension(originalFilename).toLowerCase();
                for (String s : IMG_EXTENSION) {
                    if (s.equals(ext)) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                }
            }
        }
    }

    public void badFileFilter(List<MultipartFile> multipartFiles) {
        boolean isValid = true;
        for (MultipartFile file : multipartFiles) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String ext = extractExtension(originalFilename).toLowerCase();
                for (String s : BAD_EXTENSION) {
                    if (ext.equals(s)) {
                        isValid = false;
                        break;
                    }
                }
            }
        }
        if (!isValid) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    private static String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}

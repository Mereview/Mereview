package com.ssafy.mereview.api.controller;

import com.ssafy.mereview.common.util.file.UploadFile;
import com.ssafy.mereview.domain.member.entity.ProfileImage;
import com.ssafy.mereview.domain.member.repository.ProfileImageRepository;
import com.ssafy.mereview.domain.review.entity.BackgroundImage;
import com.ssafy.mereview.domain.review.repository.command.BackgroundImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image/download")
public class ImageController {

    private final ProfileImageRepository profileImageRepository;
    private final BackgroundImageRepository backgroundImageRepository;

    @Value("${file.dir}")
    public String fileDir;

    @GetMapping("/profiles/{id}")
    public void downloadProfileImage(HttpServletResponse response, @PathVariable Long id) throws IOException {
        ProfileImage profileImage = profileImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 파일입니다."));
        UploadFile uploadFile = profileImage.getUploadFile();
        Path saveFilePath = Paths.get(fileDir + uploadFile.getStoreFileName());

        isExistFile(saveFilePath);

        setFileHeader(response, uploadFile);

        fileCopy(response, saveFilePath);
    }

    @GetMapping("/backgrounds/{id}")
    public void downloadBackgroundImage(HttpServletResponse response, @PathVariable Long id) throws IOException {
        BackgroundImage backgroundImage = backgroundImageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 파일입니다."));
        UploadFile uploadFile = backgroundImage.getUploadFile();
        Path saveFilePath = Paths.get(fileDir + uploadFile.getStoreFileName());

        isExistFile(saveFilePath);

        setFileHeader(response, uploadFile);

        fileCopy(response, saveFilePath);
    }

    private void setFileHeader(HttpServletResponse response, UploadFile uploadFile) {
        response.setHeader("Content-Disposition", "image; filename=\"" + URLEncoder.encode(uploadFile.getUploadFileName(), UTF_8) + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", "application/download; utf-8");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
    }

    private void isExistFile(Path saveFilePath) throws NoSuchFileException {
        if (!saveFilePath.toFile().exists()) {
            throw new NoSuchFileException("존재하지 않는 파일입니다.");
        }
    }

    private void fileCopy(HttpServletResponse response, Path saveFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(saveFilePath.toFile());
        FileCopyUtils.copy(fis, response.getOutputStream());
        response.getOutputStream().flush();
        fis.close();
    }
}


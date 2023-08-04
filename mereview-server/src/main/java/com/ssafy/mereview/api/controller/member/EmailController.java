package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.EmailCheckRequest;
import com.ssafy.mereview.api.service.member.EmailService;
import com.ssafy.mereview.common.response.ApiResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"이메일 체크 API"})
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ApiResponse<String> sendEmail(@RequestBody EmailCheckRequest request) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(request.getEmail());

        return ApiResponse.ok("이메일이 발송되었습니다.");
    }

    @PostMapping("/check")
    public ApiResponse<String> checkEmail(@RequestBody EmailCheckRequest request) throws MessagingException, UnsupportedEncodingException {
        boolean checkEmail = emailService.checkEmail(request.getEmail(), request.getVerificationCode());

        if(!checkEmail){
            throw new IllegalArgumentException("잘못된 코드를 입력하였습니다.");
        }
        return ApiResponse.ok("이메일 인증이 완료되었습니다.");
    }
}

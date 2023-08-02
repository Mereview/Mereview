package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.EmailCheckRequest;
import com.ssafy.mereview.api.controller.member.dto.request.FollowRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberRegisterRequest;
import com.ssafy.mereview.api.service.member.EmailService;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.MemberLoginResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final EmailService emailService;

    private final FileStore fileStore;

    private final FileExtensionFilter fileExtFilter;

    @PostMapping("/sign-up")
    public ApiResponse<Long> signup(@Valid @RequestPart(name = "request") MemberRegisterRequest request,
                                    @RequestPart(name = "file", required = false) MultipartFile file) throws Exception {

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        MemberCreateServiceRequest saveMemberServiceRequest = request.toServiceRequest(uploadFile);
        log.debug("MemberRegisterRequest : {}", request);

        Long memberId = memberService.createMember(saveMemberServiceRequest);
        if (memberId == -1) {
            throw new DuplicateRequestException("중복되는 회원이 존재합니다.");
        }
        return ApiResponse.ok(memberId);
    }

    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {
        log.debug("MemberLoginRequest : {}", request);
        MemberLoginResponse memberLoginResponse = memberService.login(request);
        return ApiResponse.ok(memberLoginResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> searchMemberInfo(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        MemberResponse memberResponse = memberService.searchMemberInfo(memberId);
        memberService.updateViewCount(memberId);
        return ApiResponse.ok(memberResponse);
    }

    @PostMapping("/follow")
    public void follow(@RequestBody FollowRequest followRequest) {
        log.debug("MemberController.follow : {}", followRequest);
        memberService.follow(followRequest.getTargetId(), followRequest.getMemberId());
    }


    private UploadFile createUploadFile(MultipartFile file) throws IOException {
        UploadFile uploadFile = null;
        if (file != null && !file.isEmpty()) {
            fileExtFilter.imageFilter(file);
            uploadFile = fileStore.storeFile(file);
        }
        return uploadFile;
    }
}

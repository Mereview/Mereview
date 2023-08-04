package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.FollowRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberLoginRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberRegisterRequest;
import com.ssafy.mereview.api.controller.member.dto.request.MemberUpdateRequest;
import com.ssafy.mereview.api.service.member.MemberQueryService;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.MemberLoginResponse;
import com.ssafy.mereview.api.service.member.dto.response.MemberResponse;
import com.ssafy.mereview.api.service.movie.GenreSaveService;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.sun.jdi.request.DuplicateRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"회원 관련 API"})
public class MemberController {

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    private final FileStore fileStore;

    private final FileExtensionFilter fileExtFilter;

    private final GenreSaveService genreSaveService;

    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입", response = Join.class)
    public ApiResponse<Long> signup(@Valid @RequestPart(name = "request") MemberRegisterRequest request,
                                    @RequestPart(name = "file", required = false) MultipartFile file) throws Exception {

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        MemberCreateServiceRequest saveMemberServiceRequest = request.toServiceRequest(uploadFile);
        log.debug("MemberRegisterRequest : {}", request);
        log.debug("MemberCreateServiceRequest : {}", saveMemberServiceRequest);


        Long memberId = memberService.createMember(saveMemberServiceRequest);
        if (memberId == -1) {
            throw new DuplicateRequestException("중복되는 회원이 존재합니다.");
        }
        return ApiResponse.ok(memberId);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", response = Join.class)
    public ApiResponse<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {

        log.debug("MemberLoginRequest : {}", request);
        MemberLoginResponse memberLoginResponse = memberQueryService.login(request);
        return ApiResponse.ok(memberLoginResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "회원 정보 조회", response = Join.class)
    public ApiResponse<MemberResponse> searchMemberInfo(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        MemberResponse memberResponse = memberQueryService.searchMemberInfo(memberId);
        memberService.updateViewCount(memberId);
        return ApiResponse.ok(memberResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "회원 정보 수정", response = Join.class)
    public ApiResponse<Long> updateMemberInfo(@PathVariable Long id,@RequestBody MemberUpdateRequest request) {
        log.debug("MemberController.updateMemberInfo : {}", request);
        Long memberId = memberService.updateMember(id, request.toMemberCreateServiceRequest());
        return ApiResponse.ok(memberId);
    }

    @PutMapping("/profile-image")
    @ApiOperation(value = "프로필 사진 업데이트", response = Join.class)
    public ApiResponse<String> updateProfilePic(@RequestPart(name = "file") MultipartFile file,
                                                @RequestPart(name = "memberId") Long memberId) throws IOException {
        log.debug("MemberController.updateProfilePic : {}", memberId);

        UploadFile uploadFile = createUploadFile(file);
        memberService.updateProfileImage(memberId, uploadFile);
        return ApiResponse.ok("프로필 사진 업데이트 성공");
    }

    @PostMapping("/follow")
    @ApiOperation(value = "팔로우", response = Join.class)
    public void follow(@RequestBody FollowRequest followRequest) {
        log.debug("MemberController.follow : {}", followRequest);
        memberService.createFollow(followRequest.getTargetId(), followRequest.getMemberId());
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

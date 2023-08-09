package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.*;
import com.ssafy.mereview.api.service.member.MemberQueryService;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
import com.sun.jdi.request.DuplicateRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/forbidden")
    @ApiOperation(value = "권한 없음", response = Join.class)
    public ApiResponse<String> forbidden() {
        return ApiResponse.of(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입", response = Join.class)
    public ApiResponse<Long> signup(@Valid @RequestPart(name = "request") MemberRegisterRequest request,
                                    @RequestPart(name = "file", required = false) MultipartFile file) throws Exception {

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        MemberCreateServiceRequest saveMemberServiceRequest = request.toServiceRequest(uploadFile);
        log.debug("MemberRegisterRequest : {}", request);
        log.debug("MemberCreateServiceRequest : {}", saveMemberServiceRequest);

        memberService.searchExistMember(request.toServiceRequest(uploadFile));


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

    @PostMapping("/introduce")
    @ApiOperation(value = "회원 자기소개 수정", response = Join.class)
    public ApiResponse<Long> updateMemberIntroduce(@RequestBody MemberIntroduceRequest request, HttpServletRequest httpServletRequest) {
        log.debug("MemberIntroduceRequest : {}", request);
        String token = httpServletRequest.getHeader("Authorization");
        if(token == null){
            return ApiResponse.of(HttpStatus.BAD_REQUEST,"토큰이 없습니다.", null);
        }
        token = token.substring("Bearer ".length()).trim();

        Long memberId = memberService.updateMemberIntroduce(request, token);
        return ApiResponse.ok(memberId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "회원 탈퇴", response = Join.class)
    public ApiResponse<Long> deleteMember(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring("Bearer ".length()).trim();

        //jwt 토큰으로 현재 접속한 유저인지 확인 후 삭제
        memberService.deleteMember(id, token);

        return ApiResponse.ok(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "회원 정보 조회", response = Join.class)
    public ApiResponse<MemberResponse> searchMemberInfo(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        MemberResponse memberResponse = memberQueryService.searchMemberInfo(memberId);
        memberService.updateViewCount(memberId);
        return ApiResponse.ok(memberResponse);
    }

    @GetMapping("/{id}/info")
    @ApiOperation(value = "회원 정보 조회", response = Join.class)
    public ApiResponse<MemberDataResponse> searchMemberInfoData(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        MemberDataResponse memberDataResponse = memberQueryService.searchMemberData(memberId);
        return ApiResponse.ok(memberDataResponse);
    }

    @GetMapping("/{id}/following")
    @ApiOperation(value = "회원 팔로우 정보 조회", response = Join.class)
    public ApiResponse<List<FollowingResponse>> searchMemberFollowInfo(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        List<FollowingResponse> followingResponse = memberQueryService.searchFollowingResponse(memberId);
        return ApiResponse.ok(followingResponse);
    }

    @GetMapping("/{id}/follower")
    @ApiOperation(value = "회원 팔로우 정보 조회", response = Join.class)
    public ApiResponse<List<FollowerResponse>> searchMemberFollowerInfo(@PathVariable(name = "id") Long memberId) {
        log.debug("MemberController.getMemberInfo : {}", memberId);
        List<FollowerResponse> followerResponse = memberQueryService.searchFollowerResponse(memberId);
        return ApiResponse.ok(followerResponse);
    }

    @GetMapping("/{id}/genre/{genreNumber}")
    @ApiOperation(value = "회원 장르 별 정보", response = Join.class)
    public ApiResponse<List<MemberTierResponse>> searchInfoByGenre(@PathVariable(name = "id") Long memberId,
                                                                   @PathVariable(name = "genreNumber") int genreNumber) {
        log.debug("MemberController.searchInfoByGenre : {}", memberId);
        return ApiResponse.ok(memberQueryService.searchMemberTierByGenre(memberId, genreNumber));
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
    public ApiResponse<MemberFollowResponse> follow(@RequestBody FollowRequest followRequest) {
        log.debug("MemberController.follow : {}", followRequest);
        MemberFollowResponse response = memberService.createFollow(followRequest.getTargetId(), followRequest.getMemberId());
        return ApiResponse.ok(response);
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

package com.ssafy.mereview.api.controller.member;

import com.ssafy.mereview.api.controller.member.dto.request.*;
import com.ssafy.mereview.api.service.member.MemberQueryService;
import com.ssafy.mereview.api.service.member.MemberService;
import com.ssafy.mereview.api.service.member.dto.request.MemberCreateServiceRequest;
import com.ssafy.mereview.api.service.member.dto.request.MemberVerifyRequest;
import com.ssafy.mereview.api.service.member.dto.response.*;
import com.ssafy.mereview.common.response.ApiResponse;
import com.ssafy.mereview.common.util.file.FileExtensionFilter;
import com.ssafy.mereview.common.util.file.FileStore;
import com.ssafy.mereview.common.util.file.UploadFile;
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
    public ApiResponse<Long> signup(@Valid @RequestPart(name = "request") MemberCreateRequest request,
                                    @RequestPart(name = "file", required = false) MultipartFile file) throws Exception {

        UploadFile uploadFile = createUploadFile(file);
        log.debug("uploadFile: {}", uploadFile);

        MemberCreateServiceRequest serviceRequest = request.toServiceRequest();
        log.debug("MemberRegisterRequest : {}", request);
        log.debug("MemberCreateServiceRequest : {}", serviceRequest);

        memberService.searchExistMember(serviceRequest);

        Long memberId = memberService.createMember(serviceRequest, uploadFile);

        return ApiResponse.ok(memberId);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", response = Join.class)
    public ApiResponse<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest request) {

        log.debug("MemberLoginRequest : {}", request);
        MemberLoginResponse memberLoginResponse = memberQueryService.login(request);
        return ApiResponse.ok(memberLoginResponse);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "회원 비밀번호 체크", response = Join.class)
    public ApiResponse<Boolean> verifyMember(@RequestBody MemberVerifyRequest request) {
        log.debug("MemberController.checkMember : {}", request);
        Boolean isVerified = memberQueryService.verifyMember(request);
        return ApiResponse.ok(isVerified);
    }

    @PostMapping("/introduce")
    @ApiOperation(value = "회원 자기소개 수정", response = Join.class)
    public ApiResponse<Long> updateMemberIntroduce(@RequestBody MemberIntroduceRequest request, HttpServletRequest httpServletRequest) {
        log.debug("MemberIntroduceRequest : {}", request);
        String token = httpServletRequest.getHeader("Authorization");
        log.debug("token : {}", token);
        if(token == null){
            return ApiResponse.of(HttpStatus.BAD_REQUEST,"토큰이 없습니다.", null);
        }

        Long memberId = memberService.updateMemberIntroduce(request, token);
        return ApiResponse.ok(memberId);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "회원 정보 조회", response = Join.class)
    public ApiResponse<MemberResponse> searchMemberInfo(@PathVariable(name = "id") Long memberId, HttpServletRequest request) {
        log.debug("MemberController.getMemberInfo : {}", memberId);

        String token = request.getHeader("Authorization");
        log.debug("token : {}", token);
        if(token == null){
            return ApiResponse.of(HttpStatus.BAD_REQUEST,"토큰이 없습니다.", null);
        }



        MemberResponse memberResponse = memberQueryService.searchMemberInfo(memberId);
        memberService.updateViewCount(memberId, token);

        return ApiResponse.ok(memberResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "회원 탈퇴", response = Join.class)
    public ApiResponse<Long> deleteMember(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        //jwt 토큰으로 현재 접속한 유저인지 확인 후 삭제
        memberService.deleteMember(id, token);

        return ApiResponse.ok(id);
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

    @PutMapping("/interests")
    @ApiOperation(value = "회원 정보 수정", response = Join.class)
    public ApiResponse<Long> updateMemberInfo(@RequestBody MemberInterestUpdateRequest request) {
        log.debug("MemberController.updateMemberInfo : {}", request);
        Long memberId = memberService.updateMemberInterest(request.getId(), request.toServiceRequest());
        return ApiResponse.ok(memberId);
    }

    @PutMapping("/nickname")
    @ApiOperation(value = "회원 정보 수정", response = Join.class)
    public ApiResponse<Long> updateMemberNickname(@RequestBody MemberNicknameUpdateRequest request) {
        log.debug("MemberController.updateMemberInfo : {}", request);
        Long memberId = memberService.updateMemberNickname(request.getId(), request.toServiceRequest());
        return ApiResponse.ok(memberId);
    }

    @PutMapping("/profile-image")
    @ApiOperation(value = "프로필 사진 업데이트", response = Join.class)
    public ApiResponse<String> updateProfilePic(@RequestPart(name = "file") MultipartFile file,
                                                @RequestPart(name = "memberId") String memberId) throws IOException {
        Long memberLong = Long.valueOf(memberId);
        log.debug("MemberController.updateProfilePic : {}", memberId);

        UploadFile uploadFile = createUploadFile(file);
        memberService.updateProfileImage(memberLong, uploadFile);
        return ApiResponse.ok("프로필 사진 업데이트 성공");
    }

    @PostMapping("/follow")
    @ApiOperation(value = "팔로우", response = Join.class)
    public ApiResponse<MemberFollowResponse> follow(@RequestBody FollowRequest followRequest) {
        log.debug("MemberController.follow : {}", followRequest);
        MemberFollowResponse response = memberService.createFollow(followRequest.getTargetId(), followRequest.getMemberId());
        return ApiResponse.ok(response);
    }

    @PutMapping("/count")
    @ApiOperation(value = "업적 카운트 증가", response = Join.class)
    public ApiResponse<Integer> updateCount(@RequestBody AchievementCountUpdateRequest request) {
        log.debug("MemberController.updateCount : {}", request);
        int achievementCount = memberService.updateAchievementCount(request.toServiceRequest());
        return ApiResponse.ok(achievementCount);
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

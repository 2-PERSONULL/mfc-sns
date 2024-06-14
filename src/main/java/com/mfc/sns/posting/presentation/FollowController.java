package com.mfc.sns.posting.presentation;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponse;
import com.mfc.sns.posting.application.FollowService;
import com.mfc.sns.posting.dto.req.FollowReqDto;
import com.mfc.sns.posting.vo.req.FollowReqVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
@Tag(name = "follow", description = "팔로우 서비스 컨트롤러")
public class FollowController {
	private final FollowService followService;
	private final ModelMapper modelMapper;

	@PostMapping
	@Operation(summary = "파트너 팔로우 등록 API", description = "파트너 팔로우 등록")
	public BaseResponse<Void> createFollow(
			@RequestHeader(value = "UUID", defaultValue = "") String userId,
			@RequestBody FollowReqVo vo) {
		checkUuid(userId);
		followService.createFollow(userId, modelMapper.map(vo, FollowReqDto.class));
		return new BaseResponse<>();
	}

	@DeleteMapping
	@Operation(summary = "파트너 팔로우 취소 API", description = "파트너 팔로우 취소")
	public BaseResponse<Void> deleteFollow(
			@RequestHeader(value = "UUID", defaultValue = "") String userId,
			@RequestBody FollowReqVo vo) {
		followService.deleteFollow(userId, modelMapper.map(vo, FollowReqDto.class));
		return new BaseResponse<>();
	}

	@GetMapping("/list")
	@Operation(summary = "특정 파트너 팔로우 여부 조회 API", description = "특정 파트너 팔로우 여부")
	public BaseResponse<Boolean> isFollowed(
			@RequestHeader(value = "UUID", defaultValue = "") String userId,
			@RequestHeader(value = "partnerId", defaultValue = "") String partnerId) {
		checkUuid(userId);
		return new BaseResponse<>(followService.isFollowed(userId, partnerId));
	}

	private void checkUuid(String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}
	}
}

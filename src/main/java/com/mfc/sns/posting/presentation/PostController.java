package com.mfc.sns.posting.presentation;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponse;
import com.mfc.sns.posting.application.PostService;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.vo.req.UpdatePostReqVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	private final PostService postService;
	private final ModelMapper modelMapper;

	@PostMapping
	public BaseResponse<Void> createPost(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody UpdatePostReqVo vo) {

		checkUuid(uuid);
		postService.createPost(uuid, modelMapper.map(vo, UpdatePostReqDto.class));
		return new BaseResponse<>();
	}

	private void checkUuid(String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}
	}
}

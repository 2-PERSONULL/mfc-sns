package com.mfc.sns.posting.presentation;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponse;
import com.mfc.sns.posting.application.PostService;
import com.mfc.sns.posting.dto.req.DeletePostReqDto;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.vo.req.DeletePostReqVo;
import com.mfc.sns.posting.vo.req.UpdatePostReqVo;
import com.mfc.sns.posting.vo.resp.PostDetailRespVo;
import com.mfc.sns.posting.vo.resp.PostListRespVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "posts", description = "포스팅 서비스 컨트롤러")
public class PostController {
	private final PostService postService;
	private final ModelMapper modelMapper;

	@PostMapping
	@Operation(summary = "포스팅 등록 API", description = "이미지 + 태그 업로드")
	public BaseResponse<Void> createPost(
			@RequestHeader(value = "UUID", defaultValue = "") String uuid,
			@RequestBody UpdatePostReqVo vo) {

		checkUuid(uuid);
		postService.createPost(uuid, modelMapper.map(vo, UpdatePostReqDto.class));
		return new BaseResponse<>();
	}

	@GetMapping("/{postId}")
	@Operation(summary = "포스팅 상세 정보 조회 API", description = "각 포스팅 상세 보기")
	public BaseResponse<PostDetailRespVo> getPostDetail(@PathVariable Long postId) {
		return new BaseResponse<>(modelMapper.map(
				postService.getPostDetail(postId), PostDetailRespVo.class));
	}

	@GetMapping("/list")
	@Operation(summary = "파트너 별 포스팅 목록 조회 API", description = "파트너 별 포스팅 목록 (sort는 지우면 됨)")
	public BaseResponse<PostListRespVo> getPostList(
			@RequestHeader(value = "UUID", defaultValue = "") String partnerId,
			@PageableDefault(size = 3, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page) {
		return new BaseResponse<>(modelMapper.map(
				postService.getPostList(partnerId, page), PostListRespVo.class));
	}

	@DeleteMapping
	@Operation(summary = "포스팅 삭제 API", description = "포스팅 (다중) 삭제")
	public BaseResponse<Void> deletePosts(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestBody DeletePostReqVo vo) {
		checkUuid(uuid);
		postService.deletePosts(uuid, modelMapper.map(vo, DeletePostReqDto.class));
		return new BaseResponse<>();
	}

	@PutMapping("/{postId}")
	@Operation(summary = "포스팅 수정 API", description = "포스팅 수정")
	public BaseResponse<Void> updatePost(@PathVariable Long postId,
			@RequestHeader(name = "UUID", defaultValue = "") String partnerId,
			@RequestBody UpdatePostReqVo vo) {
		checkUuid(partnerId);
		postService.updatePost(postId, partnerId, modelMapper.map(vo, UpdatePostReqDto.class));
		return new BaseResponse<>();
	}

	private void checkUuid(String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}
	}
}

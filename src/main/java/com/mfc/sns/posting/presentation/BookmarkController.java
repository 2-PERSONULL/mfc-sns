package com.mfc.sns.posting.presentation;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.sns.common.response.BaseResponse;
import com.mfc.sns.posting.application.BookmarkService;
import com.mfc.sns.posting.vo.resp.PostListRespVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
@Tag(name = "bookmark", description = "좋아요 서비스 컨트롤러")
public class BookmarkController {
	private final BookmarkService bookmarkService;
	private final ModelMapper modelMapper;

	@PostMapping("/{postId}")
	@Operation(summary = "포스팅 좋아요 등록 API", description = "포스팅 좋아요 등록")
	public BaseResponse<Void> createBookmark(@PathVariable Long postId,
			@RequestHeader(value = "UUID", defaultValue = "") String userId) {
		bookmarkService.createBookmark(postId, userId);
		return new BaseResponse<>();
	}

	@DeleteMapping("/{postId}")
	@Operation(summary = "포스팅 좋아요 취소 API", description = "포스팅 좋아요 취소")
	public BaseResponse<Void> deleteBookmark(@PathVariable Long postId,
			@RequestHeader(value = "UUID", defaultValue = "") String userId) {
		bookmarkService.deleteBookmark(postId, userId);
		return new BaseResponse<>();
	}

	@GetMapping("/{postId}")
	@Operation(summary = "특정 포스팅 좋아요 여부 조회 API", description = "특정 포스팅 좋아요 여부")
	public BaseResponse<Boolean> isBookmarked(@PathVariable Long postId,
			@RequestHeader(value = "UUID", required = false) String userId) {
		return new BaseResponse<>(bookmarkService.isBookmarked(postId, userId));
	}

	@GetMapping("/list")
	@Operation(summary = "유저 별 좋아요 목록 조회 API", description = "유저 별 좋아요 한 포스팅 목록")
	public BaseResponse<PostListRespVo> getBookmarkList(
			@RequestHeader(value = "UUID", defaultValue = "") String userId,
			@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable page) {
		return new BaseResponse<>(modelMapper.map(
				bookmarkService.getBookmarkList(userId, page), PostListRespVo.class));
	}
}

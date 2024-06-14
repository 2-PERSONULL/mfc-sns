package com.mfc.sns.posting.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.sns.common.response.BaseResponse;
import com.mfc.sns.posting.application.BookmarkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
@Tag(name = "bookmark", description = "좋아요 서비스 컨트롤러")
public class BookmarkController {
	private final BookmarkService bookmarkService;

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
}

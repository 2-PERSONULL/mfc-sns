package com.mfc.sns.posting.application;

import org.springframework.data.domain.Pageable;

import com.mfc.sns.posting.dto.resp.PostListRespDto;

public interface BookmarkService {
	void createBookmark(Long postId, String userId);
	void deleteBookmark(Long postId, String userId);
	boolean isBookmarked(Long postId, String userId);
	PostListRespDto getBookmarkList(String userId, Pageable page);
}

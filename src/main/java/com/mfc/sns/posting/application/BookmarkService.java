package com.mfc.sns.posting.application;

public interface BookmarkService {
	void createBookmark(Long postId, String userId);
	void deleteBookmark(Long postId, String userId);
	boolean isBookmarked(Long postId, String userId);
}

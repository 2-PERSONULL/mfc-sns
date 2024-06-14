package com.mfc.sns.posting.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.posting.domain.Bookmark;
import com.mfc.sns.posting.infrastructure.BookmarkRepository;
import com.mfc.sns.posting.infrastructure.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	@Override
	public void createBookmark(Long postId, String userId) {
		bookmarkRepository.save(Bookmark.builder()
				.userId(userId)
				.post(postId)
				.build());
	}

	@Override
	public void deleteBookmark(Long postId, String userId) {
		bookmarkRepository.deleteByPostId(postId, userId);
	}

	@Override
	public boolean isBookmarked(Long postId, String userId) {
		return bookmarkRepository.findByPostIdAndUserId(postId, userId).isPresent();
	}
}

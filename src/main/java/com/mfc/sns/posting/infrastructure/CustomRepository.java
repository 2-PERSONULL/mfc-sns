package com.mfc.sns.posting.infrastructure;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.dto.resp.PostDto;

public interface CustomRepository {
	Slice<PostDto> getBookmarkedPostList(String userId, Pageable page);
	Slice<PostDto> getExplorePostList(List<String> partners, Pageable page, String search);
	List<Post> getPostOrderByBookmark(List<Long> postIds, String search);
}

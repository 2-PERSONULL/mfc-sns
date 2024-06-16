package com.mfc.sns.posting.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.mfc.sns.posting.dto.resp.PostDto;

public interface CustomRepository {
	Slice<PostDto> getPostList(String userId, Pageable page);
}

package com.mfc.sns.posting.application;

import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;
import com.mfc.sns.posting.dto.resp.PostListRespDto;

public interface PostService {
	void createPost(String uuid, UpdatePostReqDto dto);
	PostDetailRespDto getPostDetail(Long postId);
	PostListRespDto getPostList(String partnerId);
}

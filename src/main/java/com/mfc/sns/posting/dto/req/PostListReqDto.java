package com.mfc.sns.posting.dto.req;

import java.util.List;

import lombok.Getter;

@Getter
public class PostListReqDto {
	private List<Long> posts;
	private Boolean last;
}

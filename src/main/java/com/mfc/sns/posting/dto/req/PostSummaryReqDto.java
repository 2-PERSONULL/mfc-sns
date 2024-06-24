package com.mfc.sns.posting.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSummaryReqDto {
	private Long postId;
	private Integer bookmarkCnt;
}

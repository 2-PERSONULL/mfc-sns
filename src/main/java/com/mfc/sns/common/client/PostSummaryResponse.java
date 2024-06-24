package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.PostSummaryReqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSummaryResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private PostSummaryReqDto result;
}

package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.PartnerProfilesReqDto;
import com.mfc.sns.posting.dto.req.PostListReqDto;

import lombok.Getter;

@Getter
public class PostListResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private PostListReqDto result;
}

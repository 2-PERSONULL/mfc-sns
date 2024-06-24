package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.ProfileReqDto;

import lombok.Getter;

@Getter
public class PartnerProfileResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private ProfileReqDto result;
}

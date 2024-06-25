package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.PartnerProfilesReqDto;

import lombok.Getter;

@Getter
public class PartnerProfilesResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private PartnerProfilesReqDto result;
}

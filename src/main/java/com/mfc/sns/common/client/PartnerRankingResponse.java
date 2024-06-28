package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.PartnerRankingReqDto;

import lombok.Getter;

@Getter
public class PartnerRankingResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private PartnerRankingReqDto result;
}

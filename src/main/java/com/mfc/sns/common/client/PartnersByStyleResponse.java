package com.mfc.sns.common.client;

import com.mfc.sns.posting.dto.req.PartnersByStyleReqDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class PartnersByStyleResponse {
	private String httpStatus;
	private boolean isSuccess;
	private String message;
	private int code;
	private PartnersByStyleReqDto result;
}

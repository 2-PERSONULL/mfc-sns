package com.mfc.sns.posting.dto.req;

import lombok.Getter;

@Getter
public class PartnerSummaryReqDto {
	private String partnerId;
	private Integer followerCnt;
	private Integer coordinateCnt;
	private Double averageStar;
}

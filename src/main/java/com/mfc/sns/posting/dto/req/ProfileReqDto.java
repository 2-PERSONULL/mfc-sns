package com.mfc.sns.posting.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileReqDto {
	private String nickname;
	private String profileImage;
	private String imageAlt;
}

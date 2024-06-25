package com.mfc.sns.posting.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDto {
	private String partnerId;
	private String nickname;
	private String profileImage;
	private String imageAlt;
}

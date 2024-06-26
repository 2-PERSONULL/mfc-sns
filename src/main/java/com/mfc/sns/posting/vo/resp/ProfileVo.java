package com.mfc.sns.posting.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileVo {
	private String partnerId;
	private String nickname;
	private String profileImage;
	private String imageAlt;
}

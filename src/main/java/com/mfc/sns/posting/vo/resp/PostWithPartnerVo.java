package com.mfc.sns.posting.vo.resp;

import java.util.List;

import lombok.Getter;

@Getter
public class PostWithPartnerVo {
	private Long postId;
	private String partnerId;
	private String profileImage;
	private String profileAlt;
	private String nickname;
	private String imageUrl;
	private String imageAlt;
	private List<TagVo> tags;
}

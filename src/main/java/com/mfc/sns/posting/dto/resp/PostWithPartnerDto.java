package com.mfc.sns.posting.dto.resp;

import java.util.List;

import com.mfc.sns.posting.vo.resp.PostDetailRespVo;
import com.mfc.sns.posting.vo.resp.TagVo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostWithPartnerDto {
	private Long postId;
	private String partnerId;
	private String profileImage;
	private String profileAlt;
	private String nickname;
	private String imageUrl;
	private String imageAlt;
	private List<TagDto> tags;
}

package com.mfc.sns.posting.vo.resp;

import java.util.List;

import com.mfc.sns.posting.dto.resp.PostWithPartnerDto;

import lombok.Getter;

@Getter
public class FollowedPostListRespVo {
	private List<PostWithPartnerDto> posts;
}

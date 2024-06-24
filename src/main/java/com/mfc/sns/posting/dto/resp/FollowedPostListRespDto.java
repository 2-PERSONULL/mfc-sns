package com.mfc.sns.posting.dto.resp;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowedPostListRespDto {
	private List<PostWithPartnerDto> posts;
}

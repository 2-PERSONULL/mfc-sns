package com.mfc.sns.posting.dto.resp;

import java.util.List;

import com.mfc.sns.posting.dto.req.ProfileDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowListRespDto {
	private List<ProfileDto> partners;
	private boolean isLast;
}

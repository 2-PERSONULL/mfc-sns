package com.mfc.sns.posting.vo.resp;

import java.util.List;

import lombok.Getter;

@Getter
public class FollowListRespVo {
	private List<String> partners;
	private boolean isLast;
}

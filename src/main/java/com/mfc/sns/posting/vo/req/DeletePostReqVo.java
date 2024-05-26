package com.mfc.sns.posting.vo.req;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DeletePostReqVo {
	private List<Long> posts = new ArrayList<>();
}

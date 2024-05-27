package com.mfc.sns.posting.vo.req;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class UpdatePostReqVo {
	private String imageUrl;
	private List<String> tags = new ArrayList<>();
}

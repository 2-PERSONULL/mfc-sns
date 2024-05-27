package com.mfc.sns.posting.vo.resp;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PostDetailRespVo {
	private Long postId;
	private String imageUrl;
	private String alt;
	private int bookmarkCnt;
	private List<TagVo> tags = new ArrayList<>();
}

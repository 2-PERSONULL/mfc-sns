package com.mfc.sns.posting.vo.resp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PostDetailRespVo {
	private Long postId;
	private String imageUrl;
	private String alt;
	private Integer bookmarkCnt;
	private LocalDateTime createdAt;
	private List<TagVo> tags = new ArrayList<>();
}

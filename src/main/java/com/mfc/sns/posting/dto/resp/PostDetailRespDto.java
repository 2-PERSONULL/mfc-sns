package com.mfc.sns.posting.dto.resp;

import java.time.LocalDateTime;
import java.util.List;

import com.mfc.sns.posting.vo.resp.TagVo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailRespDto {
	private Long postId;
	private String imageUrl;
	private String alt;
	private Integer bookmarkCnt;
	private LocalDateTime createdAt;
	private List<TagDto> tags;
}

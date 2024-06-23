package com.mfc.sns.posting.dto.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostDto {
	private Long postId;
}

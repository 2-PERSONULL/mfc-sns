package com.mfc.sns.posting.dto.resp;

import com.mfc.sns.posting.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {
	private Long postId;
	private String imageUrl;
	private String alt;

	public PostDto(Post post) {
		this.postId = post.getId();
		this.imageUrl = post.getImageUrl();
		this.alt = post.getAlt();
	}
}

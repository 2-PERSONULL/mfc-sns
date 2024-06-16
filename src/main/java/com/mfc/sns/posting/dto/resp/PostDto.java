package com.mfc.sns.posting.dto.resp;

import com.mfc.sns.posting.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {
	private String partnerId;
	private Long postId;
	private String imageUrl;
	private String alt;

	public PostDto(Post post) {
		this.partnerId = post.getPartnerId();
		this.postId = post.getId();
		this.imageUrl = post.getImageUrl();
		this.alt = post.getAlt();
	}
}

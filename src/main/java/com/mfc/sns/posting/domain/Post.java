package com.mfc.sns.posting.domain;

import static jakarta.persistence.GenerationType.*;

import com.mfc.sns.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@Column(updatable = false)
	private String partnerId;
	private String imageUrl;
	private String alt;

	@Builder
	public Post(Long id, String partnerId, String imageUrl, String alt) {
		this.id = id;
		this.partnerId = partnerId;
		this.imageUrl = imageUrl;
		this.alt = alt;
	}
}

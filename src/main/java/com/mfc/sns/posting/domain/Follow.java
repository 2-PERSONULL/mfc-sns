package com.mfc.sns.posting.domain;

import com.mfc.sns.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String userId;
	@Column(nullable = false, length = 50)
	private String partnerId;

	@Builder
	public Follow(String userId, String partnerId) {
		this.userId = userId;
		this.partnerId = partnerId;
	}
}

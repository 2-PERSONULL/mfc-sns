package com.mfc.sns.posting.dto.resp;

import com.mfc.sns.posting.domain.Tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TagDto {
	private Long tagId;
	private String value;

	public TagDto(Tag tag) {
		this.tagId = tag.getId();
		this.value = tag.getValue();
	}
}

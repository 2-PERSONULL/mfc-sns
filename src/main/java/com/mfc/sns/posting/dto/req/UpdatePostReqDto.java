package com.mfc.sns.posting.dto.req;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class UpdatePostReqDto {
	private String imageUrl;
	private List<String> tags = new ArrayList<>();
}

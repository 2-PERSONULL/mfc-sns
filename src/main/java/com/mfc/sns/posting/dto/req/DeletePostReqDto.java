package com.mfc.sns.posting.dto.req;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class DeletePostReqDto {
	private List<Long> posts = new ArrayList<>();
}

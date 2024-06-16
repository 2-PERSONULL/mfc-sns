package com.mfc.sns.posting.dto.kafka;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerListDto {
	List<String> partners;
}

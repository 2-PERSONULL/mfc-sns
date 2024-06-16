package com.mfc.sns.posting.application;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mfc.sns.posting.dto.kafka.PartnerListDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
	private List<String> partners;

	@KafkaListener(topics = "partnerList", containerFactory = "partnerListListener")
	public void getPartnerList(PartnerListDto dto) {
		this.partners = dto.getPartners();
	}
}

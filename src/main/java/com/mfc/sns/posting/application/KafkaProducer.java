package com.mfc.sns.posting.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.sns.posting.dto.kafka.StyleCategoryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void sendStyleId(StyleCategoryDto dto) {
		kafkaTemplate.send("styleCategory", dto);
	}
}

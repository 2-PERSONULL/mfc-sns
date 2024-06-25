package com.mfc.sns.posting.application;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.mfc.sns.posting.dto.kafka.PartnerSummaryDto;
import com.mfc.sns.posting.dto.kafka.PostSummaryDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public void createPost(PostSummaryDto dto) {
		kafkaTemplate.send("create-post", dto);
	}

	public void deletePost(PostSummaryDto dto) {
		kafkaTemplate.send("delete-post", dto);
	}

	public void createBookmark(PostSummaryDto dto) {
		kafkaTemplate.send("create-bookmark", dto);
	}

	public void deleteBookmark(PostSummaryDto dto) {
		kafkaTemplate.send("delete-bookmark", dto);
	}
}

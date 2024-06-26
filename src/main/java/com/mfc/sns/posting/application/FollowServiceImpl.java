package com.mfc.sns.posting.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.client.MemberClient;
import com.mfc.sns.common.client.PartnerProfilesResponse;
import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponseStatus;
import com.mfc.sns.posting.domain.Follow;
import com.mfc.sns.posting.dto.kafka.PartnerSummaryDto;
import com.mfc.sns.posting.dto.req.FollowReqDto;
import com.mfc.sns.posting.dto.req.ProfileDto;
import com.mfc.sns.posting.dto.resp.FollowListRespDto;
import com.mfc.sns.posting.infrastructure.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final FollowRepository followRepository;
	private final KafkaProducer producer;
	private final MemberClient memberClient;

	@Override
	public void createFollow(String userId, FollowReqDto dto) {
		if(followRepository.existsByUserIdAndPartnerId(userId, dto.getPartnerId())) {
			throw new BaseException(BaseResponseStatus.FOLLOW_CONFLICT);
		}

		followRepository.save(Follow.builder()
				.userId(userId)
				.partnerId(dto.getPartnerId())
				.build()
		);

		producer.createFollow(PartnerSummaryDto.builder()
				.partnerId(dto.getPartnerId())
				.build());
	}

	@Override
	public void deleteFollow(String userId, FollowReqDto dto) {
		Integer cnt = followRepository.deleteByUserIdAndPartnerId(userId, dto.getPartnerId());

		if(cnt > 0) {
			producer.deleteFollow(PartnerSummaryDto.builder()
					.partnerId(dto.getPartnerId())
					.build());
		}
	}

	@Override
	public boolean isFollowed(String userId, String partnerId) {
		return followRepository.existsByUserIdAndPartnerId(userId, partnerId);
	}

	@Override
	public FollowListRespDto getFollowList(String userId, Pageable page) {
		Page<Follow> follows = followRepository.findByUserId(userId, page);

		return FollowListRespDto.builder()
				.partners(memberClient
						.getPartnerProfiles(follows.stream()
								.map(Follow::getPartnerId)
								.toList())
						.getResult()
						.getProfiles())
				.isLast(follows.isLast())
				.build();
	}
}

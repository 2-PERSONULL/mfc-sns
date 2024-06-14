package com.mfc.sns.posting.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponseStatus;
import com.mfc.sns.posting.domain.Follow;
import com.mfc.sns.posting.dto.req.FollowReqDto;
import com.mfc.sns.posting.infrastructure.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
	private final FollowRepository followRepository;

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
	}

	@Override
	public void deleteFollow(String userId, FollowReqDto dto) {
		followRepository.deleteByUserIdAndPartnerId(userId, dto.getPartnerId());
	}
}

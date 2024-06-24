package com.mfc.sns.posting.application;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.client.BatchClient;
import com.mfc.sns.common.client.MemberClient;
import com.mfc.sns.common.client.PartnersByStyleResponse;
import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.posting.domain.Follow;
import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.domain.Tag;
import com.mfc.sns.posting.dto.kafka.PostSummaryDto;
import com.mfc.sns.posting.dto.req.DeletePostReqDto;
import com.mfc.sns.posting.dto.req.ProfileReqDto;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.FollowedPostListRespDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;
import com.mfc.sns.posting.dto.resp.PostDto;
import com.mfc.sns.posting.dto.resp.PostListRespDto;
import com.mfc.sns.posting.dto.resp.PostWithPartnerDto;
import com.mfc.sns.posting.dto.resp.TagDto;
import com.mfc.sns.posting.infrastructure.FollowRepository;
import com.mfc.sns.posting.infrastructure.PostRepository;
import com.mfc.sns.posting.infrastructure.TagRepository;
import com.mfc.sns.posting.vo.resp.FollowedPostListRespVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final TagRepository tagRepository;
	private final FollowRepository followRepository;

	private final MemberClient memberClient;
	private final BatchClient batchClient;
	private final KafkaProducer producer;

	@Override
	public void createPost(String uuid, UpdatePostReqDto dto) {
		Post post = postRepository.save(Post.builder()
				.imageUrl(dto.getImageUrl())
				.partnerId(uuid)
				.alt(uuid + "post")
				.build());

		insertTags(dto.getTags(), post);
		producer.createPost(PostSummaryDto.builder().postId(post.getId()).build());
	}

	@Override
	@Transactional(readOnly = true)
	public PostDetailRespDto getPostDetail(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BaseException(POST_NOT_FOUND));

		Integer bookmarkCnt = batchClient.getBookmarkCnt(postId).getResult().getBookmarkCnt();

		return PostDetailRespDto.builder()
				.postId(postId)
				.imageUrl(post.getImageUrl())
				.alt(post.getAlt())
				.tags(tagRepository.findByPostId(postId)
						.stream()
						.map(TagDto::new)
						.toList())
				.bookmarkCnt(bookmarkCnt)
				.build();
	}

	@Override
	@Transactional(readOnly = true)
	public PostListRespDto getPostList(String partnerId, Pageable page) {
		Page<Post> posts = postRepository.findByPartnerId(partnerId, page);

		return PostListRespDto.builder()
				.posts(posts.stream()
						.map(PostDto::new)
						.toList())
				.isLast(posts.isLast())
				.build();
	}

	@Override
	public void deletePosts(String uuid, DeletePostReqDto dto) {
		tagRepository.deleteTags(dto.getPosts());
		postRepository.deletePosts(dto.getPosts());
	}

	@Override
	public void updatePost(Long postId, String partnerId, UpdatePostReqDto dto) {
		Post post = postRepository.findByIdAndPartnerId(postId, partnerId)
				.orElseThrow(() -> new BaseException(POST_NOT_FOUND));

		tagRepository.deleteTagsByPostId(postId);
		postRepository.save(Post.builder()
				.id(postId)
				.imageUrl(dto.getImageUrl())
				.alt(post.getAlt())
				.build()
		);

		insertTags(dto.getTags(), post);
	}

	@Override
	@Transactional(readOnly = true)
	public PostListRespDto getExploreList(Pageable page, Long styleId) {
		List<String> partners = null;
		if(styleId != null){
			PartnersByStyleResponse result = memberClient.getPartnersByStyle(styleId);
			partners = result.getResult().getPartners();
		}

		Slice<PostDto> posts = postRepository.getExplorePostList(partners, page);

		return PostListRespDto.builder()
				.posts(posts.stream().toList())
				.isLast(posts.isLast())
				.build();
	}

	@Override
	public FollowedPostListRespDto getFollowedPostList(String userId) {
		List<String> partners = followRepository.findPartnersByUserId(userId);

		Pageable pageable = PageRequest.of(0, 12);
		List<Post> posts = postRepository.findByPartners(partners, pageable);

		List<PostWithPartnerDto> list = posts.stream()
				.map(post -> {
					ProfileReqDto profile = memberClient.getPartnerProfile(post.getPartnerId()).getResult();

					return PostWithPartnerDto.builder()
							.postId(post.getId())
							.tags(tagRepository.findByPostId(post.getId())
									.stream()
									.map(TagDto::new)
									.toList())
							.partnerId(post.getPartnerId())
							.profileImage(profile.getProfileImage())
							.profileAlt(profile.getImageAlt())
							.imageUrl(post.getImageUrl())
							.imageAlt(post.getAlt())
							.nickname(profile.getNickname())
							.build();
				}).toList();

		return FollowedPostListRespDto.builder()
				.posts(list)
				.build();
	}

	private void insertTags(List<String> tags, Post post) {
		tags
				.forEach(tag -> tagRepository.save(Tag.builder()
						.value(tag)
						.post(post)
						.build()));
	}
}

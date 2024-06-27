package com.mfc.sns.posting.application;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.client.BatchClient;
import com.mfc.sns.common.client.MemberClient;
import com.mfc.sns.common.client.PartnersByStyleResponse;
import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.domain.Tag;
import com.mfc.sns.posting.dto.kafka.PostSummaryDto;
import com.mfc.sns.posting.dto.req.DeletePostReqDto;
import com.mfc.sns.posting.dto.req.PostListReqDto;
import com.mfc.sns.posting.dto.req.ProfileDto;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.HomePostListRespDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;
import com.mfc.sns.posting.dto.resp.PostDto;
import com.mfc.sns.posting.dto.resp.PostListRespDto;
import com.mfc.sns.posting.dto.resp.PostWithPartnerDto;
import com.mfc.sns.posting.dto.resp.TagDto;
import com.mfc.sns.posting.infrastructure.FollowRepository;
import com.mfc.sns.posting.infrastructure.PostRepository;
import com.mfc.sns.posting.infrastructure.TagRepository;

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

		producer.createPost(PostSummaryDto.builder()
				.postId(post.getId())
				.partnerId(post.getPartnerId())
				.build());
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
				.createdAt(post.getCreatedAt())
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
		Integer cnt = postRepository.deletePosts(dto.getPosts());

		if(cnt > 0) {
			Long postId = dto.getPosts().get(0);
			producer.deletePost(PostSummaryDto.builder()
					.postId(postId)
					.partnerId(uuid)
					.build());
		}

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

		String sort = page.getSort().toString().split(":")[0];
		if(sort.equals("BOOKMARK")) {
			PostListReqDto result = batchClient.getPostList(page, partners).getResult();
			List<Post> posts = postRepository.getPostOrderByBookmark(result.getPosts());

			return PostListRespDto.builder()
					.posts(posts.stream()
							.map(PostDto::new)
							.toList())
					.isLast(result.getLast())
					.build();
		}

		Slice<PostDto> posts = postRepository.getExplorePostList(partners, page);
		return PostListRespDto.builder()
				.posts(posts.stream().toList())
				.isLast(posts.isLast())
				.build();
	}

	@Override
	public HomePostListRespDto getFollowedPostList(String userId) {
		// 1 : 팔로우 한 파트너 ID 목록 조회
		List<String> partners = followRepository.findPartnersByUserId(userId);

		// 2 : 포스팅 12개 조회
		Pageable pageable = PageRequest.of(0, 12);
		List<Post> posts = postRepository.findByPartners(partners, pageable);

		return HomePostListRespDto.builder()
				.posts(getList(posts))
				.build();
	}

	@Override
	public HomePostListRespDto getStylePostList(String userId) {
		// 1 + 2 : 유저의 선호 스타일에 해당하는 파트너 ID 목록 조회
		List<String> partners = memberClient.getPartnersByStyles(userId).getResult().getPartners();

		// 3 : 포스팅 10개 랜덤 조회
		List<Post> posts = postRepository.findRandomByPartners(partners);

		return HomePostListRespDto.builder()
				.posts(getList(posts))
				.build();
	}

	private void insertTags(List<String> tags, Post post) {
		tags
				.forEach(tag -> tagRepository.save(Tag.builder()
						.value(tag)
						.post(post)
						.build()));
	}

	private List<String> extractPartnerIds(List<Post> posts) {
		return posts.stream()
				.map(Post::getPartnerId)
				.distinct().toList();
	}

	private List<Long> extractPostIds(List<Post> posts) {
		return posts.stream()
				.map(Post::getId).toList();
	}

	private Map<String, ProfileDto> getProfiles(List<String> partnerIds) {
		return memberClient.getPartnerProfiles(partnerIds).getResult().getProfiles()
				.stream()
				.collect(Collectors.toMap(ProfileDto::getPartnerId, dto -> dto));
	}

	private Map<Long, List<TagDto>> getTags(List<Long> postIds) {
		return tagRepository.findByPostIds(postIds).stream()
				.collect(Collectors.groupingBy(
						tag -> tag.getPost().getId(),
						Collectors.mapping(TagDto::new, Collectors.toList())
				));
	}

	private List<PostWithPartnerDto> getList(List<Post> posts) {
		List<String> partnerIds = extractPartnerIds(posts);
		List<Long> postIds = extractPostIds(posts);

		Map<String, ProfileDto> profiles = getProfiles(partnerIds);
		Map<Long, List<TagDto>> postTags = getTags(postIds);

		return posts.stream()
				.map(post -> {
					ProfileDto profile = profiles.get(post.getPartnerId());
					List<TagDto> tags = postTags.getOrDefault(post.getId(), Collections.emptyList());

					return PostWithPartnerDto.builder()
							.postId(post.getId())
							.tags(tags)
							.partnerId(post.getPartnerId())
							.profileImage(profile.getProfileImage())
							.profileAlt(profile.getImageAlt())
							.imageUrl(post.getImageUrl())
							.imageAlt(post.getAlt())
							.nickname(profile.getNickname())
							.build();
				}).toList();
	}
}

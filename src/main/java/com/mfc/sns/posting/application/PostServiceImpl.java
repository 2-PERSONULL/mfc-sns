package com.mfc.sns.posting.application;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.domain.Tag;
import com.mfc.sns.posting.dto.kafka.PartnerListDto;
import com.mfc.sns.posting.dto.kafka.StyleCategoryDto;
import com.mfc.sns.posting.dto.req.DeletePostReqDto;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;
import com.mfc.sns.posting.dto.resp.PostDto;
import com.mfc.sns.posting.dto.resp.PostListRespDto;
import com.mfc.sns.posting.dto.resp.TagDto;
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

	private final KafkaProducer producer;
	private final KafkaConsumer consumer;

	@Override
	public void createPost(String uuid, UpdatePostReqDto dto) {
		Post post = postRepository.save(Post.builder()
				.imageUrl(dto.getImageUrl())
				.partnerId(uuid)
				.alt(uuid + "post")
				.build());

		insertTags(dto.getTags(), post);
	}

	@Override
	@Transactional(readOnly = true)
	public PostDetailRespDto getPostDetail(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BaseException(POST_NOT_FOUND));

		return PostDetailRespDto.builder()
				.postId(postId)
				.imageUrl(post.getImageUrl())
				.alt(post.getAlt())
				.tags(tagRepository.findByPostId(postId)
						.stream()
						.map(TagDto::new)
						.toList())
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
			log.info("send message");
			producer.sendStyleId(StyleCategoryDto.builder().styleId(styleId).build());
			partners = consumer.getPartners();
		}

		Slice<PostDto> posts = postRepository.getExplorePostList(partners, page);

		return PostListRespDto.builder()
				.posts(posts.stream().toList())
				.isLast(posts.isLast())
				.build();
	}

	private void insertTags(List<String> tags, Post post) {
		tags
				.forEach(tag -> tagRepository.save(Tag.builder()
						.value(tag)
						.post(post)
						.build()));
	}

	public List<String> getPartners(PartnerListDto dto) {
		return dto.getPartners();
	}
}

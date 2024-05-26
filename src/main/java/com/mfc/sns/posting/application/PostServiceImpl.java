package com.mfc.sns.posting.application;

import static com.mfc.sns.common.response.BaseResponseStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfc.sns.common.exception.BaseException;
import com.mfc.sns.common.response.BaseResponseStatus;
import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.domain.Tag;
import com.mfc.sns.posting.dto.req.UpdatePostReqDto;
import com.mfc.sns.posting.dto.resp.PostDetailRespDto;
import com.mfc.sns.posting.dto.resp.TagDto;
import com.mfc.sns.posting.infrasturctual.PostRepository;
import com.mfc.sns.posting.infrasturctual.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final TagRepository tagRepository;

	@Override
	public void createPost(String uuid, UpdatePostReqDto dto) {
		Post post = postRepository.save(Post.builder()
				.imageUrl(dto.getImageUrl())
				.partnerId(uuid)
				.alt(uuid + "post")
				.bookmarkCnt(0)
				.build());

		dto.getTags().stream()
				.forEach(tag -> tagRepository.save(Tag.builder()
								.value(tag)
								.post(post)
								.build()));
	}

	@Override
	@Transactional(readOnly = true)
	public PostDetailRespDto getPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new BaseException(POST_NOT_FOUND));

		return PostDetailRespDto.builder()
				.postId(postId)
				.imageUrl(post.getImageUrl())
				.alt(post.getAlt())
				.bookmarkCnt(post.getBookmarkCnt())
				.tags(tagRepository.findByPostId(postId)
						.stream()
						.map(tag -> new TagDto(tag.getId(), tag.getValue()))
						.toList())
				.build();
	}
}

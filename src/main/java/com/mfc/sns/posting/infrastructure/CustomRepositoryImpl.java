package com.mfc.sns.posting.infrastructure;

import static com.mfc.sns.posting.domain.QBookmark.*;
import static com.mfc.sns.posting.domain.QPost.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.mfc.sns.posting.dto.resp.PostDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {
	private final JPAQueryFactory query;

	@Override
	public Slice<PostDto> getBookmarkedPostList(String userId, Pageable page) {
		List<PostDto> result = query
				.select(Projections.constructor(PostDto.class,
						post.partnerId.as("partnerId"),
						post.id.as("postId"),
						post.imageUrl.as("imageUrl"),
						post.alt.as("alt")))
				.from(bookmark)
				.join(post)
				.on(bookmark.postId.eq(post.id))
				.where(bookmark.userId.eq(userId))
				.offset(page.getOffset())
				.limit(page.getPageSize() + 1)
				.orderBy(bookmark.createdAt.desc())
				.fetch();

		boolean hasNext = false;
		if(result.size() > page.getPageSize()) {
			result.remove(page.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(result, page, hasNext);
	}

	@Override
	public Slice<PostDto> getExplorePostList(List<String> partners, Pageable page) {

		List<PostDto> result = query
				.select(Projections.constructor(PostDto.class,
						post.partnerId.as("partnerId"),
						post.id.as("postId"),
						post.imageUrl.as("imageUrl"),
						post.alt.as("alt")))
				.from(post)
				.where(partnerIdIn(partners))
				.offset(page.getOffset())
				.limit(page.getPageSize() + 1)
				.orderBy(post.createdAt.desc())
				.fetch();

		boolean hasNext = false;
		if(result.size() > page.getPageSize()) {
			result.remove(page.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(result, page, hasNext);
	}

	private BooleanExpression partnerIdIn(List<String> partners) {
		return partners != null ? post.partnerId.in(partners) : null;
	}
}

package com.mfc.sns.posting.infrastructure;

import static com.mfc.sns.posting.domain.QBookmark.*;
import static com.mfc.sns.posting.domain.QPost.*;
import static com.mfc.sns.posting.domain.QTag.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.mfc.sns.posting.domain.Post;
import com.mfc.sns.posting.domain.QTag;
import com.mfc.sns.posting.dto.resp.PostDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
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
	public Slice<PostDto> getExplorePostList(List<String> partners, Pageable page, String search) {

		List<PostDto> result = query
				.select(Projections.constructor(PostDto.class,
						post.partnerId.as("partnerId"),
						post.id.as("postId"),
						post.imageUrl.as("imageUrl"),
						post.alt.as("alt")))
				.from(post)
				.join(tag)
				.on(post.id.eq(tag.post.id))
				.where(partnerIdIn(partners), tagEq(search))
				.offset(page.getOffset())
				.limit(page.getPageSize() + 1)
				.groupBy(post.id)
				.orderBy(post.createdAt.desc())
				.fetch();

		boolean hasNext = false;
		if(result.size() > page.getPageSize()) {
			result.remove(page.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(result, page, hasNext);
	}

	@Override
	public List<Post> getPostOrderByBookmark(List<Long> postIds, String search) {
		if (postIds == null || postIds.isEmpty()) { // 해당하는 포스팅이 없으면 빈 리스트 반환
			return List.of();
		}

		NumberExpression<Long> orderCases = Expressions.numberTemplate(Long.class, "0");

		for (int i = 0; i < postIds.size(); i++) {
			orderCases = new CaseBuilder()
					.when(post.id.eq(postIds.get(i))).then((long) i)
					.otherwise(orderCases);
		}

		return query.selectFrom(post)
				.where(post.id.in(postIds), tagEq(search))
				.join(tag)
				.on(post.id.eq(tag.post.id))
				.orderBy(orderCases.asc())
				.groupBy(post.id)
				.fetch();
	}

	private BooleanExpression partnerIdIn(List<String> partners) {
		return partners != null ? post.partnerId.in(partners) : null;
	}

	private BooleanExpression tagEq(String search) {
		return search != null ? tag.value.like("%" + search + "%") : null;
	}
}

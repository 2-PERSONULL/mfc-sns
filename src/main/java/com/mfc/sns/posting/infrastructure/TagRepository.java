package com.mfc.sns.posting.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByPostId(Long postId);

	@Modifying(flushAutomatically = true)
	@Query("delete from Tag t where t.post.id in :posts")
	void deleteTags(@Param("posts") List<Long> posts);

	@Modifying(flushAutomatically = true)
	@Query("delete from Tag t where t.post.id = :postId")
	void deleteTagsByPostId(@Param("postId") Long postId);

	@Query("SELECT t FROM Tag t WHERE t.post.id IN :postIds")
	List<Tag> findByPostIds(@Param("postIds") List<Long> postIds);
}

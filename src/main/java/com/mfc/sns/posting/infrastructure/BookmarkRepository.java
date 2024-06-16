package com.mfc.sns.posting.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, CustomRepository {
	@Modifying
	@Query("DELETE FROM Bookmark b where b.postId = :postId and b.userId = :userId")
	void deleteByPostId(@Param("postId") Long postId, @Param("userId") String userId);

	Optional<Bookmark> findByPostIdAndUserId(Long postId, String userId);

	boolean existsByPostIdAndUserId(Long postId, String userId);
}

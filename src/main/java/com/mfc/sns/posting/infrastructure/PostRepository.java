package com.mfc.sns.posting.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Post;

import jakarta.ws.rs.POST;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByPartnerId(String partnerId);

	Optional<Post> findByIdAndPartnerId(Long postId, String partnerId);

	@Modifying(clearAutomatically = true)
	@Query("delete from Post p where p.id in :posts")
	void deletePosts(@Param("posts") List<Long> posts);
}

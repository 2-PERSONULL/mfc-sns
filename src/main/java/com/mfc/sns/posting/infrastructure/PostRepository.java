package com.mfc.sns.posting.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>, CustomRepository {
	Page<Post> findByPartnerId(String partnerId, Pageable page);

	Optional<Post> findByIdAndPartnerId(Long postId, String partnerId);

	@Query("SELECT p FROM Post p WHERE p.partnerId IN :partners ORDER BY p.createdAt DESC")
	List<Post> findByPartners(@Param("partners") List<String> partners, Pageable page);

	@Modifying(clearAutomatically = true)
	@Query("delete from Post p where p.id in :posts")
	void deletePosts(@Param("posts") List<Long> posts);
}

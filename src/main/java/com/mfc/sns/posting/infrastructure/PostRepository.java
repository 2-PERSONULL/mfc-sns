package com.mfc.sns.posting.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.sns.posting.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByPartnerId(String partnerId);
}

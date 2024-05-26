package com.mfc.sns.posting.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.sns.posting.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByPostId(Long postId);
}

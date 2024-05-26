package com.mfc.sns.posting.infrasturctual;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.sns.posting.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}

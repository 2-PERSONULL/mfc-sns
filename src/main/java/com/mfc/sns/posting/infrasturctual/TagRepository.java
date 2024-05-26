package com.mfc.sns.posting.infrasturctual;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfc.sns.posting.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

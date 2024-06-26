package com.mfc.sns.posting.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	boolean existsByUserIdAndPartnerId(String userId, String partnerId);
	Page<Follow> findByUserId(String userId, Pageable page);

	@Query("SELECT f.partnerId FROM Follow f WHERE f.userId = :userId")
	List<String> findPartnersByUserId(@Param("userId") String userId);

	@Modifying
	@Query("DELETE FROM Follow f where f.userId = :userId and f.partnerId = :partnerId")
	Integer deleteByUserIdAndPartnerId(@Param("userId") String userId, @Param("partnerId") String partnerId);
}

package com.mfc.sns.posting.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.sns.posting.domain.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	boolean existsByUserIdAndPartnerId(String userId, String partnerId);

	@Modifying
	@Query("DELETE FROM Follow f where f.userId = :userId and f.partnerId = :partnerId")
	void deleteByUserIdAndPartnerId(@Param("userId") String userId, @Param("partnerId") String partnerId);
}

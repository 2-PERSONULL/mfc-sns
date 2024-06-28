package com.mfc.sns.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "batch-service")
public interface BatchClient {
	@GetMapping("/batch/bookmark/{postId}")
	PostSummaryResponse getBookmarkCnt(@PathVariable Long postId);

	@GetMapping("/batch/explore")
	PostListResponse getPostList(Pageable page, @RequestParam List<String> partners);

	@GetMapping("/batch/ranking")
	PartnerRankingResponse getPartnerRanking();
}

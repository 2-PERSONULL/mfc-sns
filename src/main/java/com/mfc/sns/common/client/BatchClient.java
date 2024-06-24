package com.mfc.sns.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "batch-service")
public interface BatchClient {
	@GetMapping("/batch/bookmark/{postId}")
	PostSummaryResponse getBookmarkCnt(@PathVariable Long postId);
}

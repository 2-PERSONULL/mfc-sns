package com.mfc.sns.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-service")
public interface MemberClient {

	@GetMapping("/partners/{styleId}")
	PartnersByStyleResponse getPartnersByStyle(@PathVariable Long styleId);
}

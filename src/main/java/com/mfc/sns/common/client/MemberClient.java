package com.mfc.sns.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-service")
public interface MemberClient {

	@GetMapping("/partners/{styleId}")
	PartnersByStyleResponse getPartnersByStyle(@PathVariable Long styleId);

	@GetMapping("/partners/profiles")
	PartnerProfilesResponse getPartnerProfiles(@RequestParam List<String> partnerIds);

	@GetMapping("/partners/styles/{userId}")
	PartnersByStyleResponse getPartnersByStyles(@PathVariable String userId);
}

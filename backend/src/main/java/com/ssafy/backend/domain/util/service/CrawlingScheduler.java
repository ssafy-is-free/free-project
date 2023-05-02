package com.ssafy.backend.domain.util.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.user.service.BojService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final UserRepository userRepository;
	private final GithubCrawlingService githubCrawlingService;
	private final BojService bojService;

	@Scheduled(cron = "0 0 2 * * *")
	public void githubUpdate() {
		log.info("깃허브 정보 업데이트 시작");
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			githubCrawlingService.getGithubInfo(user.getNickname(), user.getId());
		}
	}

	@Scheduled(cron = "0 0 2 * * *")
	public void bojUpdate() {
		log.info("백준 정보 업데이트 시작");
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			bojService.saveId(user.getId(), user.getBojId());
		}
	}
}

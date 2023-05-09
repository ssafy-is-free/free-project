package com.ssafy.backend.domain.util.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.domain.algorithm.repository.BojRepository;
import com.ssafy.backend.domain.entity.Baekjoon;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.github.service.GithubCrawlingService;
import com.ssafy.backend.domain.job.service.JobCrawlingService;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.user.service.BojService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingScheduler {

	private final UserRepository userRepository;
	private final GithubRepository githubRepository;
	private final GithubCrawlingService githubCrawlingService;
	private final BojService bojService;
	private final BojRepository bojRepository;
	private final JobCrawlingService jobCrawlingService;

	@Scheduled(cron = "0 0 2 * * *")
	@Transactional
	public void githubUpdate() {
		log.info("깃허브 랭킹 정보 업데이트");
		List<Github> githubList = githubRepository.findAllByOrderByScoreDesc();
		int rank = 1;
		for (Github github : githubList) {
			github.updatePrevRankGithub(rank++);
		}
	}

	@Scheduled(cron = "0 0 2 * * *")
	public void githubBulkUpdate() throws InterruptedException {
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			Thread.sleep(5000);
			githubCrawlingService.updateAllGithub(user.getNickname(), user.getId());
		}
	}

	@Scheduled(cron = "0 0 2 * * *")
	public void bojUpdate() {
		log.info("백준 정보 업데이트 시작");
		List<Baekjoon> baekjoonList = bojRepository.findAllByOrderByScoreDesc();
		int rank = 1;
		for (Baekjoon baekjoon : baekjoonList) {
			baekjoon.updatePrevRankBaekjoon(rank++);
		}

		List<User> userList = userRepository.findAll();
		bojRepository.saveAll(baekjoonList);
		for (User user : userList) {
			try {
				bojService.saveId(user.getId(), user.getBojId());
			} catch (Exception e) {
				log.info("백준 아이디 이상한 유저 넘기기");
			}
		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void postingUpdate() {
		log.info("채용 공고 업데이트");
		jobCrawlingService.crawlingJobPostings();
	}
}

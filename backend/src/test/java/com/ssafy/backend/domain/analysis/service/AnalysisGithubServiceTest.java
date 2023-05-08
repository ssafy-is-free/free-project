package com.ssafy.backend.domain.analysis.service;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.domain.analysis.dto.response.CompareGithubResponse;
import com.ssafy.backend.domain.entity.Github;
import com.ssafy.backend.domain.entity.GithubLanguage;
import com.ssafy.backend.domain.entity.GithubRepo;
import com.ssafy.backend.domain.entity.JobHistory;
import com.ssafy.backend.domain.entity.JobPosting;
import com.ssafy.backend.domain.entity.Language;
import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.entity.common.LanguageType;
import com.ssafy.backend.domain.github.repository.GithubLanguageRepository;
import com.ssafy.backend.domain.github.repository.GithubRepoRepository;
import com.ssafy.backend.domain.github.repository.GithubRepository;
import com.ssafy.backend.domain.job.repository.JobHistoryRepository;
import com.ssafy.backend.domain.job.repository.JobPostingRepository;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.domain.util.repository.LanguageRepository;
import com.ssafy.backend.global.response.exception.CustomException;

@SpringBootTest
class AnalysisGithubServiceTest {

	@Autowired
	private AnalysisGithubService analysisGithubService;

	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private GithubLanguageRepository githubLanguageRepository;
	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	@Autowired
	private JobPostingRepository jobPostingRepository;

	@Autowired
	private GithubRepoRepository githubRepoRepository;
	@Autowired
	private GithubRepository githubRepository;
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	void tearDown() {
		languageRepository.deleteAllInBatch();
		githubLanguageRepository.deleteAllInBatch();
		jobHistoryRepository.deleteAllInBatch();
		jobPostingRepository.deleteAllInBatch();
		githubRepoRepository.deleteAllInBatch();
		githubRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();

	}

	@DisplayName("나와 다른 유저의 깃허브 정보를 비교할 수 있다.")
	@Test
	void compareWithOpponent() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 1000, 3, 800);
		Github yourGithub = createGithub(user2, 500, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, yourGithub));

		//깃허브 레포 데이터
		GithubRepo repo1 = createGithubRepo("repository1", myGithub);
		GithubRepo repo2 = createGithubRepo("repository2", myGithub);
		GithubRepo repo3 = createGithubRepo("repository3", yourGithub);
		githubRepoRepository.saveAll(Arrays.asList(repo1, repo2, repo3));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long opponentUserId = userRepository.findByNickname("user2").orElse(user2).getId();

		//when
		CompareGithubResponse result = analysisGithubService.compareWithOpponent(opponentUserId, myUserId);

		//then
		//language와 repository를 제외한 데이터를 제대로 가지고 오는지
		assertThat(result.getMy()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains("user1", "1", 1000.0, 3.0);

		assertThat(result.getOpponent()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains("user2", "1", 500.0, 2.0);

		//repository의 수를 제대로 가지고 오는지
		assertThat(result.getMy().getRepositories()).isEqualTo(2);
		assertThat(result.getOpponent().getRepositories()).isEqualTo(1);

	}

	@DisplayName("유저 비교에 사용된 언어 정보는 퍼센트 순으로 정렬되어있다")
	@Test
	void compareWithOpponentLanguage() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		userRepository.saveAll(Arrays.asList(user1, user2));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 1000, 3, 800);
		Github yourGithub = createGithub(user2, 500, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, yourGithub));

		// //깃허브 언어 데이터
		Language language1 = Language.builder().name("Java").type(LanguageType.GITHUB).build();
		Language language2 = Language.builder().name("Python").type(LanguageType.GITHUB).build();
		languageRepository.saveAll(Arrays.asList(language1, language2));

		long languageId1 = languageRepository.findByNameAndType("Java", LanguageType.GITHUB).get().getId();
		long languageId2 = languageRepository.findByNameAndType("Python", LanguageType.GITHUB).get().getId();

		GithubLanguage githubLanguage2 = createGithubLanguage(languageId1, 70, myGithub);
		GithubLanguage githubLanguage1 = createGithubLanguage(languageId2, 30, myGithub);
		GithubLanguage githubLanguage3 = createGithubLanguage(languageId2, 100, yourGithub);
		githubLanguageRepository.saveAll(Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long opponentUserId = userRepository.findByNickname("user2").orElse(user2).getId();

		//when
		CompareGithubResponse result = analysisGithubService.compareWithOpponent(opponentUserId, myUserId);

		//then
		assertThat(result.getMy().getLanguages().getLanguageList()).hasSize(2)
			.extracting("name", "percentage")
			.containsExactly(tuple("Java", 70.0), tuple("Python", 30.0));
		assertThat(result.getOpponent().getLanguages().getLanguageList()).hasSize(1)
			.extracting("name", "percentage")
			.containsExactly(tuple("Python", 100.0));

	}

	@DisplayName("나와 해당 공공의 지원자 전체의 깃허브 정보를 비교할 수 있다.")
	@Test
	void compareWithAllApplicant() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		User user4 = createUser("user4");
		userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 100, 3, 800);
		Github user1Github = createGithub(user2, 500, 2, 500);
		Github user2Github = createGithub(user3, 1000, 2, 500);
		Github user3Github = createGithub(user4, 1500, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, user1Github, user2Github, user3Github));

		//깃허브 레포 데이터
		GithubRepo repo1 = createGithubRepo("repository1", myGithub);
		GithubRepo repo2 = createGithubRepo("repository2", user1Github);
		GithubRepo repo3 = createGithubRepo("repository3", user1Github);
		GithubRepo repo4 = createGithubRepo("repository4", user2Github);
		GithubRepo repo5 = createGithubRepo("repository5", user3Github);
		githubRepoRepository.saveAll(Arrays.asList(repo1, repo2, repo3, repo4, repo5));

		//취업 공고
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		//공고 지원 이력
		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		JobHistory jobHistory3 = createJobHistory(user3, jobPosting1);
		JobHistory jobHistory4 = createJobHistory(user4, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2, jobHistory3, jobHistory4));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId();

		//when
		CompareGithubResponse result = analysisGithubService.compareWithAllApplicant(jobPostingId, myUserId);

		//then
		//language와 repository를 제외한 데이터를 제대로 가지고 오는지
		assertThat(result.getMy()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains("user1", "1", 100.0, 3.0);

		assertThat(result.getOpponent()).extracting("nickname", "avatarUrl", "commit", "star")
			.contains(null, null, 775.0, 2.3);

		//repository의 수를 제대로 가지고 오는지
		assertThat(result.getMy().getRepositories()).isEqualTo(1);
		assertThat(result.getOpponent().getRepositories()).isEqualTo(1.3);

	}

	@DisplayName("유저 비교에 사용된 언어 정보는 지원자 평균값이 퍼센트 순으로 정렬되어있다")
	@Test
	void compareWithAllApplicantLanguage() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		User user4 = createUser("user4");
		userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 100, 3, 800);
		Github user1Github = createGithub(user2, 500, 2, 500);
		Github user2Github = createGithub(user3, 1000, 2, 500);
		Github user3Github = createGithub(user4, 1500, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, user1Github, user2Github, user3Github));

		//취업 공고
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		//공고 지원 이력
		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		JobHistory jobHistory3 = createJobHistory(user3, jobPosting1);
		JobHistory jobHistory4 = createJobHistory(user4, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2, jobHistory3, jobHistory4));

		// 언어 종류
		Language language1 = Language.builder().name("Java").type(LanguageType.GITHUB).build();
		Language language2 = Language.builder().name("Python").type(LanguageType.GITHUB).build();
		Language language3 = Language.builder().name("C++").type(LanguageType.GITHUB).build();
		Language language4 = Language.builder().name("C").type(LanguageType.GITHUB).build();
		Language language5 = Language.builder().name("JavaScript").type(LanguageType.GITHUB).build();
		Language language6 = Language.builder().name("TypeScript").type(LanguageType.GITHUB).build();
		languageRepository.saveAll(Arrays.asList(language1, language2, language3, language4, language5, language6));

		long languageId1 = languageRepository.findByNameAndType("Java", LanguageType.GITHUB).get().getId();
		long languageId2 = languageRepository.findByNameAndType("Python", LanguageType.GITHUB).get().getId();
		long languageId3 = languageRepository.findByNameAndType("C++", LanguageType.GITHUB).get().getId();
		long languageId4 = languageRepository.findByNameAndType("C", LanguageType.GITHUB).get().getId();
		long languageId5 = languageRepository.findByNameAndType("JavaScript", LanguageType.GITHUB).get().getId();
		long languageId6 = languageRepository.findByNameAndType("TypeScript", LanguageType.GITHUB).get().getId();

		//깃허브별 언어 사용
		GithubLanguage githubLanguage1 = createGithubLanguage(languageId1, 70, myGithub);
		GithubLanguage githubLanguage2 = createGithubLanguage(languageId1, 40, user1Github);
		GithubLanguage githubLanguage3 = createGithubLanguage(languageId2, 60, user1Github);
		GithubLanguage githubLanguage4 = createGithubLanguage(languageId3, 50, user2Github);
		GithubLanguage githubLanguage5 = createGithubLanguage(languageId4, 40, user2Github);
		GithubLanguage githubLanguage6 = createGithubLanguage(languageId5, 30, user3Github);
		GithubLanguage githubLanguage7 = createGithubLanguage(languageId6, 20, user3Github);
		GithubLanguage githubLanguage8 = createGithubLanguage(languageId1, 50, user3Github);
		githubLanguageRepository.saveAll(
			Arrays.asList(githubLanguage1, githubLanguage2, githubLanguage3, githubLanguage4, githubLanguage5,
				githubLanguage6, githubLanguage7, githubLanguage8));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId();

		//when
		CompareGithubResponse result = analysisGithubService.compareWithAllApplicant(jobPostingId, myUserId);

		//then
		assertThat(result.getOpponent().getLanguages().getLanguageList()).hasSize(5)
			.extracting("name", "percentage")
			.containsExactly(tuple("Java", 40.0), tuple("Python", 15.0), tuple("C++", 12.5), tuple("C", 10.0),
				tuple("JavaScript", 7.5));

	}

	@DisplayName("잘못된 공고로는 지원자 평균 비교를 할 수 없다")
	@Test
	void compareWithAllApplicantIncorrectPosting() {
		//given
		//유저 데이터
		User user1 = createUser("user1");
		User user2 = createUser("user2");
		User user3 = createUser("user3");
		User user4 = createUser("user4");
		userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));

		//깃허브 데이터
		Github myGithub = createGithub(user1, 100, 3, 800);
		Github user1Github = createGithub(user2, 500, 2, 500);
		Github user2Github = createGithub(user3, 1000, 2, 500);
		Github user3Github = createGithub(user4, 1500, 2, 500);
		githubRepository.saveAll(Arrays.asList(myGithub, user1Github, user2Github, user3Github));

		//깃허브 레포 데이터
		GithubRepo repo1 = createGithubRepo("repository1", myGithub);
		GithubRepo repo2 = createGithubRepo("repository2", user1Github);
		GithubRepo repo3 = createGithubRepo("repository3", user1Github);
		GithubRepo repo4 = createGithubRepo("repository4", user2Github);
		GithubRepo repo5 = createGithubRepo("repository5", user3Github);
		githubRepoRepository.saveAll(Arrays.asList(repo1, repo2, repo3, repo4, repo5));

		//취업 공고
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		//공고 지원 이력
		JobHistory jobHistory1 = createJobHistory(user1, jobPosting1);
		JobHistory jobHistory2 = createJobHistory(user2, jobPosting1);
		JobHistory jobHistory3 = createJobHistory(user3, jobPosting1);
		JobHistory jobHistory4 = createJobHistory(user4, jobPosting1);
		jobHistoryRepository.saveAll(Arrays.asList(jobHistory1, jobHistory2, jobHistory3, jobHistory4));

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId() + 1;

		//then
		//when //then
		assertThatThrownBy(() -> analysisGithubService.compareWithAllApplicant(jobPostingId, myUserId)).isInstanceOf(
			CustomException.class);

	}

	@DisplayName("해당 공고에 지원하지 않은 유저는 정보를 볼 수 없다.")
	@Test
	void compareWithAllApplicantNoApplicant() {
		//given
		//유저 데이터
		User user1 = createUser("user1");

		userRepository.save(user1);

		//깃허브 데이터
		Github myGithub = createGithub(user1, 100, 3, 800);
		githubRepository.save(myGithub);

		//취업 공고
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId();

		//then
		//when //then
		assertThatThrownBy(() -> analysisGithubService.compareWithAllApplicant(jobPostingId, myUserId)).isInstanceOf(
			CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus", NOT_APPLY);

	}

	@DisplayName("해당 공고에 지원자 정보가 없으면 잘못된 요청이다.(본인이 꼭 있으니까!)")
	@Test
	void compareWithAllApplicantNotFoundApplicant() {
		//given
		//유저 데이터
		User user1 = createUser("user1");

		userRepository.save(user1);

		//깃허브 데이터
		Github myGithub = createGithub(user1, 100, 3, 800);
		githubRepository.save(myGithub);

		//취업 공고
		JobPosting jobPosting1 = createJobPosting("정승네트워크", "자바 4명~~");
		jobPostingRepository.save(jobPosting1);

		long myUserId = userRepository.findByNickname("user1").orElse(user1).getId();
		long jobPostingId = jobPostingRepository.findByName("자바 4명~~").get().getId();

		//then
		//when //then
		assertThatThrownBy(() -> analysisGithubService.compareWithAllApplicant(jobPostingId, myUserId)).isInstanceOf(
			CustomException.class).hasFieldOrPropertyWithValue("customExceptionStatus", NOT_FOUND_APPLICANT);

	}

	private User createUser(String nickname) {
		return User.builder().nickname(nickname).image("1").isDeleted(false).build();
	}

	private Github createGithub(User user, int commitTotalCount, int starTotalCount, int score) {
		return Github.builder()
			.user(user)
			.commitTotalCount(commitTotalCount)
			.followerTotalCount(10)
			.starTotalCount(starTotalCount)
			.score(score)
			.profileLink("1")
			.previousRank(1)
			.build();
	}

	private GithubLanguage createGithubLanguage(long languageId, double percentage, Github github) {
		return GithubLanguage.builder().languageId(languageId).github(github).percentage(percentage).build();
	}

	public GithubRepo createGithubRepo(String name, Github github) {
		return GithubRepo.builder().name(name).readme("hi").repositoryLink("hi").github(github).build();
	}

	private JobHistory createJobHistory(User user, JobPosting jobPosting) {
		return JobHistory.builder()
			.dDay(LocalDate.now())
			.dDayName("test")
			.statusId(1)
			.jobObjective("백엔드 개발")
			.isDeleted(false)
			.user(user)
			.jobPosting(jobPosting)
			.build();
	}

	private JobPosting createJobPosting(String companyName, String name) {
		return JobPosting.builder()
			.companyName(companyName)
			.name(name)
			.startTime(LocalDate.now())
			.endTime(LocalDate.now())
			.isClose(false)
			.build();

	}
}
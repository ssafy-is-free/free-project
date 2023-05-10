package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import com.ssafy.backend.domain.github.dto.CGithubDTO;
import com.ssafy.backend.domain.github.dto.CGithubRepoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "github")
public class Github extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "commit_total_count", nullable = false)
	private int commitTotalCount;

	@Column(name = "follower_total_count", nullable = false)
	private int followerTotalCount;

	@Column(name = "star_total_count", nullable = false)
	private int starTotalCount;

	@Column(name = "score", nullable = false)
	private int score;

	@Column(name = "profile_link", nullable = false)
	private String profileLink;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@OneToMany(mappedBy = "github", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@Builder.Default
	private Set<GithubRepo> githubRepos = new HashSet<>();

	@OneToMany(mappedBy = "github", cascade = CascadeType.PERSIST)
	@Builder.Default
	private Set<GithubLanguage> githubLanguages = new HashSet<>();

	@Column(name = "is_public")
	@Builder.Default
	private boolean isPublic = true;

	@Column(name = "do_not_user2")
	private int doNotUse2;

	@Column(name = "github_previous_rank", nullable = false)
	private long previousRank;

	public static Github create(CGithubDTO githubDTO, User user) {
		int score = calcScore(githubDTO.getCommit(), githubDTO.getFollowers(), githubDTO.getStar(),
			githubDTO.getRepositories().size());
		return Github.builder()
			.commitTotalCount(githubDTO.getCommit())
			.followerTotalCount(githubDTO.getFollowers())
			.starTotalCount(githubDTO.getStar())
			.profileLink(githubDTO.getProfileLink())
			.score(score)
			.user(user)
			.build();
	}

	public void update(CGithubDTO githubDTO,
		List<GithubLanguage> newGithubLanguageList) {
		this.score = calcScore(githubDTO.getCommit(), githubDTO.getFollowers(), githubDTO.getStar(),
			githubDTO.getRepositories().size());
		this.commitTotalCount = githubDTO.getCommit();
		this.profileLink = githubDTO.getProfileLink();
		this.starTotalCount = githubDTO.getStar();
		this.followerTotalCount = githubDTO.getFollowers();
		updateGithubRepos(githubDTO.getRepositories());
		updateGithubLanguages(newGithubLanguageList);
	}

	private void updateGithubLanguages(List<GithubLanguage> newGithubLanguageList) {
		// 기존에 있던 언어 PK 리스트
		List<Long> oldLanguageList = githubLanguages.stream()
			.map(l -> l.getLanguageId())
			.collect(Collectors.toList());

		// 새로운 언어 추가
		newGithubLanguageList.stream()
			.filter(l -> !oldLanguageList.contains(l.getLanguageId()))
			.forEach(l -> githubLanguages.add(l));

		// 기존에 있던 언어 정보 업데이트
		for (GithubLanguage githubLanguage : githubLanguages) {
			for (GithubLanguage language : newGithubLanguageList) {
				if (githubLanguage.getLanguageId() == language.getLanguageId()) {
					githubLanguage.update(language.getPercentage());
				}
			}
		}
	}

	private void updateGithubRepos(List<CGithubRepoDTO> repositories) {
		// 새로 가져온 레포들의 이름 리스트
		List<String> newRepoNameList = repositories.stream()
			.map(r -> r.getName())
			.collect(Collectors.toList());

		// 기존 레포들의 이름 리스트
		List<String> oldRepoNameList = githubRepos.stream()
			.map(r -> r.getName())
			.collect(Collectors.toList());

		// 새로 가져온 레포에 없는 경우 -> 레포지토리 삭제
		List<String> removeNameList = githubRepos.stream()
			.filter(r -> !newRepoNameList.contains(r.getName()))
			.map(r -> r.getName())
			.collect(Collectors.toList());

		for (Iterator<GithubRepo> iter = githubRepos.iterator(); iter.hasNext(); ) {
			GithubRepo githubRepo = iter.next();
			if (removeNameList.contains(githubRepo.getName())) {
				iter.remove();
			}
		}

		// 새로운 레포지토리 추가
		repositories.stream()
			.filter(r -> !oldRepoNameList.contains(r.getName()))
			.map(r -> GithubRepo.create(r.getName(), r.getReadme(), r.getLink(), this))
			.forEach(r -> githubRepos.add(r));
	}

	public static int calcScore(int commits, int followers, int stars, int repoSize) {
		return (int)(stars * 100 + commits + followers * 0.5 + repoSize * 0.1);
	}

	public void updatePrevRankGithub(long previousRank) {
		this.previousRank = previousRank;

	}

	public void updatePublic(boolean status) {

		this.isPublic = status;
	}

}

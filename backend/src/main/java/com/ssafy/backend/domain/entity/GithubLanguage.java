package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;

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
@Table(name = "github_languages")
@TableGenerator(
	name = "GITHUBLANGUAGE_SEQ_GENERATOR",
	table = "my_sequences",
	pkColumnValue = "GITHUBLANGUAGE_SEQ"
)
public class GithubLanguage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = TABLE, generator = "GITHUBLANGUAGE_SEQ_GENERATOR")
	@Column(name = "id")
	private long id;

	@Column(name = "language_id", nullable = false)
	private long languageId;

	@Column(name = "percentage", nullable = false)
	private double percentage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "github_id", nullable = false)
	private Github github;

	public static GithubLanguage create(long id, double percentage, Github github) {
		return GithubLanguage.builder().languageId(id).percentage(percentage).github(github).build();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		GithubLanguage language = (GithubLanguage)object;
		return this.getLanguageId() == language.getLanguageId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.languageId + this.percentage);
	}

	public void update(double percentage) {
		this.percentage = percentage;
	}
}

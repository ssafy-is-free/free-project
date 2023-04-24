package com.ssafy.backend.domain.entity;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "github_languages")
public class GithubLanguage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "language_id", nullable = false)
    private long languageId;

    @Column(name = "percentage", nullable = false)
    private String percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_id", nullable = false)
    private Github github;

    public static GithubLanguage create(long id, String percentage, Github github) {
        return GithubLanguage.builder()
                .languageId(id)
                .percentage(percentage)
                .github(github)
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        GithubLanguage language = (GithubLanguage) object;
        return this.getLanguageId() == language.getLanguageId() && this.getPercentage().equals(language.getPercentage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.languageId + this.percentage);
    }

}

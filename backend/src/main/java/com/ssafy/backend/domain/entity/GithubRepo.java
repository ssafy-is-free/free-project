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
@Table(name = "github_repository")
public class GithubRepo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "readme", nullable = false)
    private String readme;

    @Column(name = "repository_link", nullable = false)
    private String repositoryLink;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_id", nullable = false)
    private Github github;

    public static GithubRepo create(String name, String readme, String repositoryLink, Github github) {
        return GithubRepo.builder()
                .name(name)
                .readme(readme)
                .repositoryLink(repositoryLink)
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
        GithubRepo repo = (GithubRepo) object;
        return this.getName().equals(repo.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}

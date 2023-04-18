package com.ssafy.backend.entity;

import com.ssafy.backend.entity.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "github_repository")
public class GithubRepository extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "readme", nullable = false, columnDefinition = "LONGTEXT")
    private String readme;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_id", nullable = false)
    private Github github;
}

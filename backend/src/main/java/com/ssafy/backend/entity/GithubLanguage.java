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
    @JoinColumn(name = "github_id")
    private Github github;



}

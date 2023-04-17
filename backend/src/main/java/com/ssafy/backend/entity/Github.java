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
@Table(name = "github")
public class Github extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "commit_total_count", nullable = false)
    private int commitTotalCount;

    @Column(name = "pr_total_count", nullable = false)
    private int prTotalCount;

    @Column(name = "star_total_count", nullable = false)
    private int starTotalCount;

    @Column(name = "continuous_commit_count", nullable = false)
    private int continuousCommitCount;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "profile_link", nullable = false)
    private String profileLink;

}

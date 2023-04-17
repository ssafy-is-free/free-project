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
@Table(name = "rank")
public class Rank extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "github_current_rank", nullable = false)
    private long githubCurrentRank;

    @Column(name = "github_previous_rank", nullable = false)
    private long githubPreviousRank;

    @Column(name = "boj_current_rank", nullable = false)
    private long bojCurrentRank;

    @Column(name = "boj_previous_rank", nullable = false)
    private long bojPreviousRank;
}

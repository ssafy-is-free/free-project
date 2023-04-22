package com.ssafy.backend.domain.entity;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
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

    //엔티티 생성
    public static Rank create(){
        return Rank.builder()
                .githubCurrentRank(0)
                .githubPreviousRank(0)
                .bojCurrentRank(0)
                .bojPreviousRank(0)
                .build();
    }

}

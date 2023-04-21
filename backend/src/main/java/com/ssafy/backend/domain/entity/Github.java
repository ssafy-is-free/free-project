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

    @Column(name = "do_not_user1")
    private int doNotUse1;

    @Column(name = "do_not_user2")
    private int doNotUse2;


    public static Github create(int commits, int followers, int stars, String profileLink, User user) {
        // calc score
        int s = 0;

        return Github.builder()
                .commitTotalCount(commits)
                .followerTotalCount(followers)
                .starTotalCount(stars)
                .profileLink(profileLink)
                .score(s)
                .user(user)
                .build();
    }
}

package com.ssafy.backend.domain.entity;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "boj_id",unique = true)
    private String bojId;

    @Column(name = "is_boolean", nullable = false)
    private boolean isBoolean;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false, unique = true)
    private Rank rank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_id", nullable = false, unique = true)
    private Github github;





}

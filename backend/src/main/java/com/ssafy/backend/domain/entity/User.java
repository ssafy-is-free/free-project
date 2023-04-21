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

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "boj_id",unique = true)
    private String bojId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false, unique = true)
    private Rank rank;

    @Column(name = "do_not_use1")
    private String doNotUse1;


}

package com.ssafy.backend.domain.entity;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nickname", nullable = false, unique = true)
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


    //엔티티 생성 메서드
    public static User create(String nickname, String image, Rank rank){

        return User.builder()
                .nickname(nickname)
                .image(image)
                .rank(rank)
                .isDeleted(false)
                .build();
    }

    //로그인시 업데이트 - 닉네임, 프로필
    public void profileUpdate(String nickname, String image){

        this.nickname = nickname;
        this.image = image;

    }

    //리프레시 토큰 저장 - 업데이트
    public void updateRefreshToken(String refreshToken){

        this.refreshToken = refreshToken;
    }

    public void saveBojId(String bojId){
        this.bojId = bojId;
    }

}

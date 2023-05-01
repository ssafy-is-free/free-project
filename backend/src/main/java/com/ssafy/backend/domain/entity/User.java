package com.ssafy.backend.domain.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.backend.domain.entity.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "image", nullable = false)
	private String image;

	@Column(name = "boj_id", unique = true)
	private String bojId;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@Column(name = "do_not_use1")
	private String doNotUse1;

	//엔티티 생성 메서드
	public static User create(String nickname, String image) {

		return User.builder()
			.nickname(nickname)
			.image(image)
			.isDeleted(false)
			.build();
	}

	//로그인시 업데이트 - 닉네임, 프로필
	public void profileUpdate(String nickname, String image) {

		this.nickname = nickname;
		this.image = image;

	}

	//리프레시 토큰 저장 - 업데이트
	public void updateRefreshToken(String refreshToken) {

		this.refreshToken = refreshToken;
	}

	public void saveBojId(String bojId) {
		this.bojId = bojId;
	}

}

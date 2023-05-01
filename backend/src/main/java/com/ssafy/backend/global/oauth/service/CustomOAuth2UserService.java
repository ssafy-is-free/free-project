package com.ssafy.backend.global.oauth.service;

import java.util.Optional;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.oauth.dto.OAuth2UserInfo;
import com.ssafy.backend.global.oauth.exception.OAuth2AuthenticationProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//TODO : 이 클래스에서 트랜잭션을 붙이는 것이 좋을까?

@Slf4j
@Transactional
@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final CustomOAuthUserInfoFactory customOAuthUserInfoFactory;
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		try {

			return processOAuthUser(userRequest, oAuth2User);
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
		}
	}

	/*받아온 OAuth2User 객체에서 필요한 사용자 정보를 뽑아서 시큐리티에서 사용하는 principal*/
	public OAuth2User processOAuthUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws
		OAuth2AuthenticationProcessingException {

		//어떤 OAuthProivder인지 판단
		OAuth2UserInfo oAuth2UserInfo = customOAuthUserInfoFactory.getOAuth2UserInfo(
			oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

		//열어봤을때 아이디가 없으면 예외 던짐.
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException(
				oAuth2UserRequest.getClientRegistration().getRegistrationId() + "에서 ID를 찾을 수 없습니다.");
		}

		//유저의 닉네임으로 조회해봄
		String nickname = oAuth2UserInfo.getName();
		String image = oAuth2UserInfo.getProfileImage();

		boolean isNew = true;

		Optional<User> userOptional = userRepository.findByNicknameAndIsDeletedFalse(nickname);

		User user = null;

		//조회 했을때 유저가 있으면,
		if (userOptional.isPresent()) {
			user = userOptional.get();
			updateUser(user, nickname, image);
			isNew = false;
		}
		//조회했을 때 없으면 생성.
		else {
			user = registerUser(nickname, image);
		}

		return UserPrincipal.createUserDetails(user, isNew);
	}

	//db에 없으면 등록
	public User registerUser(String nickname, String image) {

		User user = User.create(nickname, image);
		return userRepository.save(user);
	}

	//db에 있으면 업데이트.
	public void updateUser(User user, String nickname, String image) {

		user.profileUpdate(nickname, image);

	}
}

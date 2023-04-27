package com.ssafy.backend.global.auth.filter;

import static com.ssafy.backend.global.response.exception.CustomExceptionStatus.*;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.repository.UserRepository;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.auth.util.TokenProvider;
import com.ssafy.backend.global.response.exception.CustomException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	public static final String TOKEN_EXCEPTION_KEY = "exception";
	public static final String TOKEN_INVALID = "invalid";
	public static final String TOKEN_EXPIRE = "expire";
	public static final String TOKEN_UNSUPPORTED = "unsupported";
	public static final String TOKEN_ILLEGAL = "illegal";
	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			log.info("token provider : {}", tokenProvider);

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromToken(jwt);

				UserDetails userDetails = createUserDetail(userId);

				//                UsernamePasswordAuthenticationToken authentication =
				//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userDetails, null, null);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (MalformedJwtException e) {
			log.info("유효하지 않은 토큰입니다.");
			request.setAttribute(TOKEN_EXCEPTION_KEY, TOKEN_INVALID);
		} catch (ExpiredJwtException e) {
			log.info("만료된 토큰입니다.");
			request.setAttribute(TOKEN_EXCEPTION_KEY, TOKEN_EXPIRE);
		} catch (UnsupportedJwtException e) {
			log.info("지원하지 않는 토큰입니다.");
			request.setAttribute(TOKEN_EXCEPTION_KEY, TOKEN_UNSUPPORTED);
		} catch (IllegalStateException e) {
			log.info("잘못된 토큰입니다.");
			request.setAttribute(TOKEN_EXCEPTION_KEY, TOKEN_ILLEGAL);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public UserDetails createUserDetail(long userId) {
		User user = userRepository.findByIdAndIsDeletedFalse(userId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_USER));

		return UserPrincipal.createUserDetails(user);
	}
}

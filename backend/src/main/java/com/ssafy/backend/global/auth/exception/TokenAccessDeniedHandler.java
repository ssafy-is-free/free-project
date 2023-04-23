package com.ssafy.backend.global.auth.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// TODO: 2023-04-23 json 형태를 따라서 error 일때도 별도로 메시지를 만들어서 보내줘야됨
// TODO: 2023-04-23 팀원들과 상의해서 표준예외로 갈것인지 논의 필요

/*인가 실패 - 권한이 없을때*/
@Slf4j
@RequiredArgsConstructor
@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("Access error : ", accessDeniedException.getMessage());

        // TODO: 2023-04-23 현재는 그냥 403응답만 해줌
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다");
    }
}

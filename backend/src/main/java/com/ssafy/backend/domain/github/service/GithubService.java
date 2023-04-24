package com.ssafy.backend.domain.github.service;

import com.ssafy.backend.domain.entity.User;
import com.ssafy.backend.domain.user.dto.NicknameListResponseDTO;
import com.ssafy.backend.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final UserQueryRepository userQueryRepository;

    public List<NicknameListResponseDTO> getNicknameList(String nickname) {
        List<User> userList = userQueryRepository.findByNickname(nickname);
        return userList.stream().map(
                u -> NicknameListResponseDTO.create(u.getId(), u.getNickname())
        ).collect(Collectors.toList());
    }
}

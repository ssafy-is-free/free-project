package com.ssafy.backend.domain.user.controller;

import com.ssafy.backend.domain.user.dto.BojIdRequestDTO;
import com.ssafy.backend.domain.user.service.BojService;
import com.ssafy.backend.global.auth.dto.UserPrincipal;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.ResponseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class BojController {

    private final BojService bojService;
    private final ResponseService responseService;


    @PostMapping("boj-id")
    public CommonResponse saveBojId(
            @RequestBody BojIdRequestDTO bojIdRequestDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal){

        long userId = userPrincipal.getId();

        bojService.saveId(userId,bojIdRequestDTO);

        return responseService.getSuccessResponse();
    }

    @GetMapping("boj-id")
    public CommonResponse checkBojId(
            @RequestParam("id") String bojId){

        bojService.checkDuplicateId(bojId);

        return responseService.getSuccessResponse();
    }
}

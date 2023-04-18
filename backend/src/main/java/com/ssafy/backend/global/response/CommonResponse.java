package com.ssafy.backend.global.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {

    private String status;
    private String message;
}

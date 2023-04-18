package com.ssafy.backend.global.response;

import com.ssafy.backend.global.response.ResponseStatus;
import com.ssafy.backend.global.response.CommonResponse;
import com.ssafy.backend.global.response.CustomSuccessStatus;
import com.ssafy.backend.global.response.DataResponse;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {




    /**
     * 요청 성공 응답 - 응답 데이터가 없는 경우.
     * @return
     */
    public CommonResponse getSuccessResponse(){
        CommonResponse response = new CommonResponse();
        response.setStatus(ResponseStatus.SUCCESS.toString());
        response.setMessage("요청에 성공했습니다.");
        return response;
    }

    public <T> DataResponse<T> getDataResponse(T data, CustomSuccessStatus status){
        DataResponse<T> response = new DataResponse<>();
        response.setStatus(status.getStatus().toString());
        response.setMessage(status.getMessage());
        response.setData(data);

        return response;
    }

    public CommonResponse getExceptionResponse(CustomExceptionStatus status){

        CommonResponse response = new CommonResponse();
        response.setStatus(status.getStatus().toString());
        response.setMessage(status.getMessage());

        return response;

    }
}

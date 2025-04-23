package com.sungwon.api.member.exception;

import com.sungwon.api.common.dto.ResponseDTO;

public class MemberServiceException extends RuntimeException {
    private final ResponseDTO responseDTO;

    public MemberServiceException(ResponseDTO responseDTO) {
        super(responseDTO.getMsg());
        this.responseDTO = responseDTO;
    }

    public ResponseDTO getResponseDTO() {
        return responseDTO;
    }
}

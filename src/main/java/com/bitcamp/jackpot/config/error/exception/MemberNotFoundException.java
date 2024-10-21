package com.bitcamp.jackpot.config.error.exception;

import com.bitcamp.jackpot.config.error.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(Pageable pageable, ErrorCode errorCode) {

    }

}

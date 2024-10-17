package com.bitcamp.jackpot.config.error.exception;

import com.bitcamp.jackpot.config.error.ErrorCode;

public class DuplicateResourceException extends BusinessBaseException {

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode.getMessage(),errorCode);
    }

    public DuplicateResourceException() {
        super(ErrorCode.CONFLICT);
    }
}

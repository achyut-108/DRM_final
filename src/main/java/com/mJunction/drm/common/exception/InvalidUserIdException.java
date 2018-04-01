package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 4/5/2017.
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "BAM unaware of this user id!!")
public class InvalidUserIdException extends RuntimeException {

    public InvalidUserIdException(String message){
        super(message);
    }
}

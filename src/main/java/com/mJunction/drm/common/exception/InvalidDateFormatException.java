package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 4/5/2017.
 */

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,reason = "Date format is not valid!!")
public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException(String message){
        super(message);
    }
}

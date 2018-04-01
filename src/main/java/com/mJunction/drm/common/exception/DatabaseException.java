package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */


@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE,reason = "Database is down..Please contact support!!")
public class DatabaseException extends Exception {

    public DatabaseException(String message){
        super(message);
    }

}

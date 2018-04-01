package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */


@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "Unable to Delete File...Please contact support!!")
public class FileDeleteException extends Exception {

    public FileDeleteException(String message){
        super(message);
    }

}

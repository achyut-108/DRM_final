package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */


@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Property File not Found")
public class PropertyFileNotFoundException extends Exception {

    public PropertyFileNotFoundException(String message){
        super(message);
    }

}

package com.mJunction.drm.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */


@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED,reason = "Could Not Reinitiate Bidder...Please contact support!!")
public class ReinitiateBidderException extends Exception {

    public ReinitiateBidderException(String message){
        super(message);
    }

}

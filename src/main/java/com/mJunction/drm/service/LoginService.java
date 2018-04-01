package com.mJunction.drm.service;

import com.mJunction.drm.common.exception.DatabaseException;
import com.mJunction.drm.pojo.LoginDetails;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */
public interface LoginService {

    public LoginDetails loginHelper(LoginDetails loginDetails);
    public LoginDetails reLoginHelper(LoginDetails loginDetails) throws DatabaseException;
    public LoginDetails logoutHelper(LoginDetails loginDetails) throws DatabaseException;
    public String checkPreviousLogin(LoginDetails loginDetails);
}

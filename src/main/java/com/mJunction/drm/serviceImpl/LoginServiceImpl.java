package com.mJunction.drm.serviceImpl;

import org.springframework.stereotype.Service;

import com.mJunction.drm.common.exception.DatabaseException;
import com.mJunction.drm.pojo.LoginDetails;
import com.mJunction.drm.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Override
	public LoginDetails loginHelper(LoginDetails loginDetails) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDetails reLoginHelper(LoginDetails loginDetails) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginDetails logoutHelper(LoginDetails loginDetails) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkPreviousLogin(LoginDetails loginDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}

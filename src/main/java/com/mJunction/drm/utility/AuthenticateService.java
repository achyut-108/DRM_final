package com.mJunction.drm.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * used in the respective locations
 */
@Deprecated
public class AuthenticateService {
	
	
	public static boolean authenticateServiceToken(String authToken,HttpServletRequest request,HttpServletResponse response){
		boolean respose = false;
		
		HttpSession session = request.getSession();
		String inSessionAuthtoken = (String) session.getAttribute("authtokenhash");
		if(authToken.equalsIgnoreCase(inSessionAuthtoken)){
			respose = true;
		}
		
		return respose;
		
	}

	
	

}

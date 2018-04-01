package com.mJunction.drm.utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/tokenStore")
public class TokenStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String startDate = request.getParameter("fromDate");
		String endDate = request.getParameter("toDate");
		String clientName = request.getParameter("clientName");

		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    String dates=startDate+"`"+endDate;
	    String json = new Gson().toJson(dates);
	    response.getWriter().write(json);

	}

}

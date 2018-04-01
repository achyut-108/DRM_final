package com.mJunction.drm.dashBoard;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/populateTotalActivity")
public class PopulateDetailsActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String inSessionAuthtoken = (String) session
				.getAttribute("authtokenhash");
		
		String authTokenHtml = request.getParameter("tokenValue").trim();
		

		try {

			if (authTokenHtml != null
					&& authTokenHtml.equalsIgnoreCase(inSessionAuthtoken)) {

				

				Map<String, String> options = new LinkedHashMap<>();
				options.put("totalActivities", "20");
				options.put("underProcessing", "30");
				options.put("successfulActivities", "14");
				options.put("failedActivities", "8");
				String json = new Gson().toJson(options);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}

			else {
				response.sendError(402, "error");
				response.setContentType("text/plain");
				
			}

		} catch (Exception e) {
			
			response.sendError(402, "error");
			response.setContentType("text/plain");
			response.getWriter().write("exception Occured");
		}
	}

}

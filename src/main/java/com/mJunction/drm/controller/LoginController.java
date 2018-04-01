package com.mJunction.drm.controller;

import com.mJunction.drm.common.exception.DatabaseException;
import com.mJunction.drm.common.exception.PropertyFileNotFoundException;
import com.mJunction.drm.pojo.LoginDetails;
import com.mJunction.drm.service.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by siddhartha.kumar on 3/28/2017.
 */

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private LoginService loginService;

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws ServletException
     * @throws IOException
     * @throws PropertyFileNotFoundException
     * {@link com.mJunction.bam.bamLogin.LoginServlet}
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private void login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws
            ServletException, IOException, PropertyFileNotFoundException {

        LOGGER.info("[login] : start of the method!!");

        LoginDetails loginDetails = new LoginDetails();

        /**
         * @apiNote Check of Null for fields of LoginDetails have been done in
         * the setter method of the respective fields of LoginDetails.
         * trim() method has been used in the setter method itself for
         * removing trailing spaces
         */
        loginDetails.setUserName(httpServletRequest.getParameter("userName"));
        loginDetails.setPassword(httpServletRequest.getParameter("password"));

        LOGGER.info("[login] : loginDetails before calling service : ",loginDetails.toString());

        loginDetails = this.loginService.loginHelper(loginDetails);

        LOGGER.info("[login] : loginDetails after getting results from LoginService : ",loginDetails.toString());

        this.loginAndSaveToSessionAndResponse(loginDetails,httpServletRequest,httpServletResponse);

    }


    /**
     * {@link com.mJunction.bam.bamLogin.Relogin}
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/relogin", method = RequestMethod.POST)
    private void reLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws DatabaseException, IOException {

        LoginDetails loginDetails = new LoginDetails();

        loginDetails.setUserName(httpServletRequest.getParameter("userName"));
        loginDetails.setPassword(httpServletRequest.getParameter("password"));

        loginDetails = this.loginService.reLoginHelper(loginDetails);

        LOGGER.info("[relogin] : loginDetails after getting results from LoginService : ",loginDetails.toString());

        this.loginAndSaveToSessionAndResponse(loginDetails,httpServletRequest,httpServletResponse);
    }

    /**
     * {@link com.mJunction.bam.bamLogout.LogutServlet}
     * @param httpServletResponse
     * @param httpServletRequest
     * @throws DatabaseException
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws DatabaseException {

        LOGGER.info("[logout] : start of the method !! ");

        LoginDetails loginDetails = new LoginDetails();
        HttpSession session = httpServletRequest.getSession();

        loginDetails.setUserName((String) session.getAttribute(("userid")));
        this.loginService.logoutHelper(loginDetails);



        session.invalidate();

    }


    /**
     * helper method to login and reLogin
     * @param loginDetails
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws IOException
     */
    private void loginAndSaveToSessionAndResponse(LoginDetails loginDetails,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {

        LOGGER.info("[loginAndSaveToSessionAndResponse] : start of the method!!");

        /**
         * @apiNote Check of Null for fields of LoginDetails have been done in
         * the setter method of the respective fields of LoginDetails.
         * trim() method has been used in the setter method itself for
         * removing trailing spaces
         */


        if(loginDetails.getValidationErrors()!=null && !loginDetails.getValidationErrors().isEmpty()){
            LOGGER.info("[loginAndSaveToSessionAndResponse] : validation Error(s) start :");
            loginDetails.getValidationErrors().stream().forEach(e->LOGGER.info(e.toString()));
            LOGGER.info("[loginAndSaveToSessionAndResponse] : validation Error(s) end :");
            httpServletResponse.sendError(402, "error");
            httpServletResponse.setContentType("text/plain");
            return;
        }

        httpServletResponse.setContentType("text/plain");

        if ("success".equalsIgnoreCase(loginDetails.getLoginResponse())) {
            PrintWriter out = httpServletResponse.getWriter();
            out.println(loginDetails.getEncryptedToken());
            HttpSession session = httpServletRequest.getSession();

            session.setAttribute("username", loginDetails.getUserName());
            session.setAttribute("authtokenhash", loginDetails.getEncryptedToken());
            session.setAttribute("authtokennormal", loginDetails.getDecryptedToken());
            session.setAttribute("userrole", loginDetails.getUserRole());
            session.setAttribute("userid", loginDetails.getUserName());
            session.setAttribute("checkWebserviceOrDB", loginDetails.getCheckWebserviceOrDB());
        } else if ("Not Authenticated".equalsIgnoreCase(loginDetails.getLoginResponse())) {
            httpServletResponse.sendError(404, "error");
            httpServletResponse.setContentType("text/plain");
        } else if ("error".equalsIgnoreCase(loginDetails.getLoginResponse())) {
            httpServletResponse.sendError(402, "error");
            httpServletResponse.setContentType("text/plain");
        } else {
            httpServletResponse.sendError(403, "error");
            httpServletResponse.setContentType("text/plain");
        }
    }
}

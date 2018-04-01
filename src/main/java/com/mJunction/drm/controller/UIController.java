package com.mJunction.drm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by siddhartha.kumar on 3/29/2017.
 */

@Controller
public class UIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UIController.class);

    /**
     *
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getWelcomePage(){
        LOGGER.info("[getWelcomePage] : start of the method!!");
        return "login";
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "/dashboard",method = RequestMethod.POST)
    public String getDashboardPage(){
        LOGGER.info("[getDashboardPage] : start of the method!! URL is : dasboard !!");
//        return "index";
       // return "profile";
        return "sideMenu";
    }
    
    @RequestMapping(value = "/dashboard2",method = RequestMethod.GET)
    public String getDashboardPage2(){
        LOGGER.info("[getDashboardPage] : start of the method!!");
        return "index2";
    }
    
    @RequestMapping(value = "/dashboard1",method = RequestMethod.GET)
    public String getDashboardPage1(){
        LOGGER.info("[getDashboardPage] : start of the method!!");
        return "index";
    }
}

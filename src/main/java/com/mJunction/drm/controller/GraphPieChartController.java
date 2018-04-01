package com.mJunction.drm.controller;

import com.google.gson.Gson;
import com.mJunction.drm.pojo.ActivityGraphData;
import com.mJunction.drm.pojo.GraphPieChartPojo;
import com.mJunction.drm.service.GraphPieChartService;
import com.mJunction.drm.utility.DBConnection;
import com.mJunction.drm.utility.ReadDateFormatConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by siddhartha.kumar on 3/28/2017.
 */

@Controller
public class GraphPieChartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphPieChartController.class);

    @Autowired
    private GraphPieChartService graphPieChartService;

    /**
     * ActivityGraphServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * done
     */
    @RequestMapping(value = "/activityGraphServlet",method = RequestMethod.GET)
    public void activityGraphServlet(HttpServletRequest request,
                                     HttpServletResponse response) throws ServletException, IOException {

        GraphPieChartPojo graphPieChartPojo = new GraphPieChartPojo();

        List<ActivityGraphData> graphPlotActivityListSuccess = this.graphPieChartService
                .populateSuccessActivities(graphPieChartPojo).getSuccessActivityGraphDataList();

        List<ActivityGraphData> graphPlotActivityListFail = this.graphPieChartService
                .populateFailedActivities(graphPieChartPojo).getFailedActivityGraphDataList();
        List<ActivityGraphData> graphPlotActivityListUnderProcessing = this.graphPieChartService.
                populateUnderProcessingActivities(graphPieChartPojo).getUnderProcessingActivityGraphDataList();

        Gson gson = new Gson();
        String s = "PriceTicks";
        response.setContentType("application/json");

        StringBuilder sb = new StringBuilder("{");

        String finalJsonSuccess = "{" + '"' + s + '"' + ":" + gson.toJson(graphPlotActivityListSuccess)
                + "}";
        String finalJsonFailure = "{" + '"' + s + '"' + ":" + gson.toJson(graphPlotActivityListFail)
                + "}";
        String finalJsonUnder = "{" + '"' + s + '"' + ":" + gson.toJson(graphPlotActivityListUnderProcessing)
                + "}";
        String bothJson = "[" + finalJsonSuccess + "," + finalJsonFailure + "," + finalJsonUnder + "]";

        LOGGER.info("[activityGraphServlet] : bothJson : ",bothJson);
        response.getWriter().write(bothJson);
    }

    /**
     * ActivityPieServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * done
     */
    @RequestMapping(value = "/activityPieServlet",method = RequestMethod.GET)
    public void activityPieServlet(HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException {


        GraphPieChartPojo graphPieChartPojo = new GraphPieChartPojo();

        double imaginarySucess = 0.0;
        double imaginaryFailure = 0.0;
        double imaginaryUnder = 0.0;

        double totalImaginaryCount = 0.0;
        double imaginaryMark = 100.0;

        double accutualCount = 0;

        double graphPlotPie = this.graphPieChartService.
                getCountOfSuccessfulActivitiesForPieChart(graphPieChartPojo).getTotalSuccesfulActivitiesForPieChart();
        double populateFailureListPieChart = this.graphPieChartService.
                getCountOfFailedActivitiesForPieChart(graphPieChartPojo).getTotalSuccesfulActivitiesForPieChart();
        double populateUnderProcessListPieChart = this.graphPieChartService.
                getCountOfUnderProcessingActivitiesForPieChart(graphPieChartPojo).getTotalUnderProcessingActivitiesForPieChart();

        double imaginarySucessAfterRound = 0.0;
        double imaginaryFailureAfterRound = 0.0;
        double imaginaryUnderAfterRound = 0.0;

        double countTotal = graphPlotPie + populateFailureListPieChart
                + populateUnderProcessListPieChart;

        imaginarySucess = (graphPlotPie * imaginaryMark / countTotal);
        imaginaryFailure = (populateFailureListPieChart * imaginaryMark / countTotal);
        imaginaryUnder = (populateUnderProcessListPieChart * imaginaryMark / countTotal);

        imaginarySucessAfterRound=Math.round(imaginarySucess * 100);
        imaginaryFailureAfterRound=Math.round(imaginaryFailure * 100);
        imaginaryUnderAfterRound=Math.round(imaginaryUnder * 100);

        imaginarySucessAfterRound = imaginarySucessAfterRound / 100;
        imaginaryFailureAfterRound = imaginaryFailureAfterRound / 100;
        imaginaryUnderAfterRound = imaginaryUnderAfterRound / 100;

        totalImaginaryCount = (imaginarySucessAfterRound + imaginaryFailureAfterRound + imaginaryUnderAfterRound);

        if (totalImaginaryCount > imaginaryMark) {
            accutualCount = totalImaginaryCount - imaginaryMark;

            if (graphPlotPie > populateFailureListPieChart
                    && graphPlotPie > populateUnderProcessListPieChart) {

                graphPlotPie = graphPlotPie - accutualCount;

            }

            else if (populateFailureListPieChart > graphPlotPie
                    && populateFailureListPieChart > populateUnderProcessListPieChart) {
                populateFailureListPieChart = populateFailureListPieChart
                        - accutualCount;
            } else if (populateUnderProcessListPieChart > graphPlotPie
                    && populateUnderProcessListPieChart > populateFailureListPieChart) {

                populateUnderProcessListPieChart = populateUnderProcessListPieChart
                        - accutualCount;
            }
        }

        if (totalImaginaryCount < imaginaryMark) {
            accutualCount = imaginaryMark - totalImaginaryCount;
            if (graphPlotPie > populateFailureListPieChart
                    && graphPlotPie > populateUnderProcessListPieChart) {

                graphPlotPie = graphPlotPie + accutualCount;

            }

            else if (populateFailureListPieChart > graphPlotPie
                    && populateFailureListPieChart > populateUnderProcessListPieChart) {
                populateFailureListPieChart = populateFailureListPieChart
                        + accutualCount;
            } else if (populateUnderProcessListPieChart > graphPlotPie
                    && populateUnderProcessListPieChart > populateFailureListPieChart) {

                populateUnderProcessListPieChart = populateUnderProcessListPieChart
                        + accutualCount;
            }

        }

        Gson gson = new Gson();
       // Object[] arr1 = new Object[2];
        Object[] arr1 = {new String("Successful Activity"),new Double(graphPlotPie)};
        Object[] arr2 = {new String("Failed Activity"),new Double(populateFailureListPieChart)};
        Object[] arr3 = {new String("Under Processing Activity"),new Double(populateUnderProcessListPieChart)};

        Object finalStringReturn[][] = { arr1, arr2, arr3 };
        String json = gson.toJson(finalStringReturn);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

}

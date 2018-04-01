package com.mJunction.drm.service;

import com.mJunction.drm.pojo.GraphPieChartPojo;

/**
 * Created by siddhartha.kumar on 4/12/2017.
 */
public interface GraphPieChartService {

    GraphPieChartPojo populateSuccessActivities(GraphPieChartPojo graphPieChartPojo);
    GraphPieChartPojo populateFailedActivities(GraphPieChartPojo graphPieChartPojo);
    GraphPieChartPojo populateUnderProcessingActivities(GraphPieChartPojo graphPieChartPojo);
    GraphPieChartPojo getCountOfSuccessfulActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo);
    GraphPieChartPojo getCountOfFailedActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo);
    GraphPieChartPojo getCountOfUnderProcessingActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo);
}

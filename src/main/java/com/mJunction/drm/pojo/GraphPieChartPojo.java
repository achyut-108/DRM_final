package com.mJunction.drm.pojo;

import java.util.List;

/**
 * Created by siddhartha.kumar on 4/12/2017.
 */
public class GraphPieChartPojo {

    private List<ActivityGraphData> successActivityGraphDataList;
    private List<ActivityGraphData> failedActivityGraphDataList;
    private List<ActivityGraphData> underProcessingActivityGraphDataList;
    private Integer totalSuccesfulActivitiesForPieChart;
    private Integer totalFailedActivitiesForPieChart;
    private Integer totalUnderProcessingActivitiesForPieChart;

    public Integer getTotalSuccesfulActivitiesForPieChart() {
        return totalSuccesfulActivitiesForPieChart;
    }

    public void setTotalSuccesfulActivitiesForPieChart(Integer totalSuccesfulActivitiesForPieChart) {
        this.totalSuccesfulActivitiesForPieChart = totalSuccesfulActivitiesForPieChart;
    }

    public Integer getTotalFailedActivitiesForPieChart() {
        return totalFailedActivitiesForPieChart;
    }

    public void setTotalFailedActivitiesForPieChart(Integer totalFailedActivitiesForPieChart) {
        this.totalFailedActivitiesForPieChart = totalFailedActivitiesForPieChart;
    }

    public Integer getTotalUnderProcessingActivitiesForPieChart() {
        return totalUnderProcessingActivitiesForPieChart;
    }

    public void setTotalUnderProcessingActivitiesForPieChart(Integer totalUnderProcessingActivitiesForPieChart) {
        this.totalUnderProcessingActivitiesForPieChart = totalUnderProcessingActivitiesForPieChart;
    }

    public List<ActivityGraphData> getUnderProcessingActivityGraphDataList() {
        return underProcessingActivityGraphDataList;
    }

    public void setUnderProcessingActivityGraphDataList(List<ActivityGraphData> underProcessingActivityGraphDataList) {
        this.underProcessingActivityGraphDataList = underProcessingActivityGraphDataList;
    }

    public List<ActivityGraphData> getSuccessActivityGraphDataList() {
        return successActivityGraphDataList;
    }

    public void setSuccessActivityGraphDataList(List<ActivityGraphData> successActivityGraphDataList) {
        this.successActivityGraphDataList = successActivityGraphDataList;
    }

    public List<ActivityGraphData> getFailedActivityGraphDataList() {
        return failedActivityGraphDataList;
    }

    public void setFailedActivityGraphDataList(List<ActivityGraphData> failedActivityGraphDataList) {
        this.failedActivityGraphDataList = failedActivityGraphDataList;
    }

    @Override
    public String toString() {
        return "GraphPieChartPojo{" + "successActivityGraphDataList=" + successActivityGraphDataList + ", failedActivityGraphDataList=" + failedActivityGraphDataList + ", underProcessingActivityGraphDataList=" + underProcessingActivityGraphDataList + ", totalSuccesfulActivitiesForPieChart=" + totalSuccesfulActivitiesForPieChart + ", totalFailedActivitiesForPieChart=" + totalFailedActivitiesForPieChart + ", totalUnderProcessingActivitiesForPieChart=" + totalUnderProcessingActivitiesForPieChart + '}';
    }
}

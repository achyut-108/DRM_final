package com.mJunction.drm.pojo;

import com.mJunction.drm.common.validation.ApiValidationBase;

/**
 * Created by siddhartha.kumar on 4/7/2017.
 */
public class SearchPojo extends ApiValidationBase{

    private String clientName;
    private String fromDate;
    private String toDate;
    private String jsonStringForSuccess;
    private String jsonStringForFailure;
    private String jsonStringForUnderProcessing;
    private String jsonStringForPieChart;
    private String checkWebServiceOrDb;
    private String inSessionAuthToken;
    private String authTokenHtml;
    private String jsonStringForBadgePopulation;
    private String jsonStringForListOfClients;
    private String jsonStringForOverAllClientActivity;
    private String jsonStringForFailedClientActivities;
    private String jsonStringForSuccessfulClientActivities;
    private String jsonStringForTotalClientActivities;
    private String jsonStringForUnderProcessingActivities;

    public String getJsonStringForUnderProcessingActivities() {
        return jsonStringForUnderProcessingActivities;
    }

    public void setJsonStringForUnderProcessingActivities(String jsonStringForUnderProcessingActivities) {
        this.jsonStringForUnderProcessingActivities = jsonStringForUnderProcessingActivities;
    }

    public String getJsonStringForTotalClientActivities() {
        return jsonStringForTotalClientActivities;
    }

    public void setJsonStringForTotalClientActivities(String jsonStringForTotalClientActivities) {
        this.jsonStringForTotalClientActivities = jsonStringForTotalClientActivities;
    }

    public String getJsonStringForSuccessfulClientActivities() {
        return jsonStringForSuccessfulClientActivities;
    }

    public void setJsonStringForSuccessfulClientActivities(String jsonStringForSuccessfulClientActivities) {
        this.jsonStringForSuccessfulClientActivities = jsonStringForSuccessfulClientActivities;
    }

    public String getJsonStringForFailedClientActivities() {
        return jsonStringForFailedClientActivities;
    }

    public void setJsonStringForFailedClientActivities(String jsonStringForFailedClientActivities) {
        this.jsonStringForFailedClientActivities = jsonStringForFailedClientActivities;
    }

    public String getJsonStringForOverAllClientActivity() {
        return jsonStringForOverAllClientActivity;
    }

    public void setJsonStringForOverAllClientActivity(String jsonStringForOverAllClientActivity) {
        this.jsonStringForOverAllClientActivity = jsonStringForOverAllClientActivity;
    }

    public String getJsonStringForListOfClients() {
        return jsonStringForListOfClients;
    }

    public void setJsonStringForListOfClients(String jsonStringForListOfClients) {
        this.jsonStringForListOfClients = jsonStringForListOfClients;
    }

    public String getJsonStringForBadgePopulation() {
        return jsonStringForBadgePopulation;
    }

    public void setJsonStringForBadgePopulation(String jsonStringForBadgePopulation) {
        this.jsonStringForBadgePopulation = jsonStringForBadgePopulation;
    }

    public String getCheckWebServiceOrDb() {
        return checkWebServiceOrDb;
    }

    public void setCheckWebServiceOrDb(String checkWebServiceOrDb) {
        this.checkWebServiceOrDb = checkWebServiceOrDb;
    }

    public String getInSessionAuthToken() {
        return inSessionAuthToken;
    }

    public void setInSessionAuthToken(String inSessionAuthToken) {
        this.inSessionAuthToken = inSessionAuthToken;
    }

    public String getAuthTokenHtml() {
        return authTokenHtml;
    }

    public void setAuthTokenHtml(String authTokenHtml) {
        this.authTokenHtml = authTokenHtml;
    }

    public String getJsonStringForPieChart() {
        return jsonStringForPieChart;
    }

    public void setJsonStringForPieChart(String jsonStringForPieChart) {
        this.jsonStringForPieChart = jsonStringForPieChart;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getJsonStringForSuccess() {
        return jsonStringForSuccess;
    }

    public void setJsonStringForSuccess(String jsonStringForSuccess) {
        this.jsonStringForSuccess = jsonStringForSuccess;
    }

    public String getJsonStringForFailure() {
        return jsonStringForFailure;
    }

    public void setJsonStringForFailure(String jsonStringForFailure) {
        this.jsonStringForFailure = jsonStringForFailure;
    }

    public String getJsonStringForUnderProcessing() {
        return jsonStringForUnderProcessing;
    }

    public void setJsonStringForUnderProcessing(String jsonStringForUnderProcessing) {
        this.jsonStringForUnderProcessing = jsonStringForUnderProcessing;
    }

    @Override
    public String toString() {
        return "SearchPojo{" + "clientName='" + clientName + '\'' + ", fromDate='" + fromDate + '\'' + ", toDate='" + toDate + '\'' + ", jsonStringForSuccess='" + jsonStringForSuccess + '\'' + ", jsonStringForFailure='" + jsonStringForFailure + '\'' + ", jsonStringForUnderProcessing='" + jsonStringForUnderProcessing + '\'' + ", jsonStringForPieChart='" + jsonStringForPieChart + '\'' + ", checkWebServiceOrDb='" + checkWebServiceOrDb + '\'' + ", inSessionAuthToken='" + inSessionAuthToken + '\'' + ", authTokenHtml='" + authTokenHtml + '\'' + ", jsonStringForBadgePopulation='" + jsonStringForBadgePopulation + '\'' + ", jsonStringForListOfClients='" + jsonStringForListOfClients + '\'' + ", jsonStringForOverAllClientActivity='" + jsonStringForOverAllClientActivity + '\'' + ", jsonStringForFailedClientActivities='" + jsonStringForFailedClientActivities + '\'' + ", jsonStringForSuccessfulClientActivities='" + jsonStringForSuccessfulClientActivities + '\'' + ", jsonStringForTotalClientActivities='" + jsonStringForTotalClientActivities + '\'' + ", jsonStringForUnderProcessingActivities='" + jsonStringForUnderProcessingActivities + '\'' + '}';
    }
}

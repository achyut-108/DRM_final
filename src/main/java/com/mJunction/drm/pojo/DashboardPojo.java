package com.mJunction.drm.pojo;

import java.util.Objects;

import com.mJunction.drm.common.validation.ApiValidationBase;

/**
 * Created by siddhartha.kumar on 4/5/2017.
 */
public class DashboardPojo extends ApiValidationBase{

    private String inSessionAuthToken;
    private String authTokenHtml;
    private String checkWebServiceOrDb;
    private Integer totalActivities;
    private Integer underProcessingActivities;
    private Integer successfulActivities;
    private Integer failedActivities;
    private String catCode;
    private String timeStamp;
    private String jsonOutput;

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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
        if(Objects.nonNull(authTokenHtml)){
            this.authTokenHtml = authTokenHtml.trim();
            return;
        }
        this.authTokenHtml = authTokenHtml;
    }

    public String getCheckWebServiceOrDb() {
        return checkWebServiceOrDb;
    }

    public void setCheckWebServiceOrDb(String checkWebServiceOrDb) {
        this.checkWebServiceOrDb = checkWebServiceOrDb;
    }

    public Integer getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(Integer totalActivities) {
        this.totalActivities = totalActivities;
    }

    public Integer getUnderProcessingActivities() {
        return underProcessingActivities;
    }

    public void setUnderProcessingActivities(Integer underProcessingActivities) {
        this.underProcessingActivities = underProcessingActivities;
    }

    public Integer getSuccessfulActivities() {
        return successfulActivities;
    }

    public void setSuccessfulActivities(Integer successfulActivities) {
        this.successfulActivities = successfulActivities;
    }

    public Integer getFailedActivities() {
        return failedActivities;
    }

    public void setFailedActivities(Integer failedActivities) {
        this.failedActivities = failedActivities;
    }

    public String getJsonOutput() {
        return jsonOutput;
    }

    public void setJsonOutput(String jsonOutput) {
        this.jsonOutput = jsonOutput;
    }
}

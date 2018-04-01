package com.mJunction.drm.pojo;

import com.mJunction.drm.common.validation.ApiValidationBase;

/**
 * Created by siddhartha.kumar on 4/11/2017.
 */
public class BidderPojo extends ApiValidationBase{

    private String clientName;
    private String inSessionAuthToken;
    private String checkWebServiceOrDb;
    private String authTokenHtml;
    private String fileName;
    private String dateTimeStamp;
    private String onlyTimeStamp;
    private String jsonStringForBidderSyncPopulation;
    private String jsonStringXmlBidder;

    public String getJsonStringXmlBidder() {
        return jsonStringXmlBidder;
    }

    public void setJsonStringXmlBidder(String jsonStringXmlBidder) {
        this.jsonStringXmlBidder = jsonStringXmlBidder;
    }

    public String getJsonStringForBidderSyncPopulation() {
        return jsonStringForBidderSyncPopulation;
    }

    public void setJsonStringForBidderSyncPopulation(String jsonStringForBidderSyncPopulation) {
        this.jsonStringForBidderSyncPopulation = jsonStringForBidderSyncPopulation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getOnlyTimeStamp() {
        return onlyTimeStamp;
    }

    public void setOnlyTimeStamp(String onlyTimeStamp) {
        this.onlyTimeStamp = onlyTimeStamp;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInSessionAuthToken() {
        return inSessionAuthToken;
    }

    public void setInSessionAuthToken(String inSessionAuthToken) {
        this.inSessionAuthToken = inSessionAuthToken;
    }

    public String getCheckWebServiceOrDb() {
        return checkWebServiceOrDb;
    }

    public void setCheckWebServiceOrDb(String checkWebServiceOrDb) {
        this.checkWebServiceOrDb = checkWebServiceOrDb;
    }

    public String getAuthTokenHtml() {
        return authTokenHtml;
    }

    public void setAuthTokenHtml(String authTokenHtml) {
        this.authTokenHtml = authTokenHtml;
    }

    @Override
    public String toString() {
        return "BidderPojo{" + "clientName='" + clientName + '\'' + ", inSessionAuthToken='" + inSessionAuthToken + '\'' + ", checkWebServiceOrDb='" + checkWebServiceOrDb + '\'' + ", authTokenHtml='" + authTokenHtml + '\'' + ", fileName='" + fileName + '\'' + ", dateTimeStamp='" + dateTimeStamp + '\'' + ", onlyTimeStamp='" + onlyTimeStamp + '\'' + ", jsonStringForBidderSyncPopulation='" + jsonStringForBidderSyncPopulation + '\'' + ", jsonStringXmlBidder='" + jsonStringXmlBidder + '\'' + '}';
    }
}

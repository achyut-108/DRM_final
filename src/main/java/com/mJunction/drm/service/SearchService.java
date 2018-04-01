package com.mJunction.drm.service;

import com.mJunction.drm.common.exception.InvalidDateFormatException;
import com.mJunction.drm.pojo.SearchPojo;

import java.text.ParseException;
import java.util.List;


/**
 * Created by siddhartha.kumar on 4/7/2017.
 */
public interface SearchService {

    SearchPojo populateActivitySuccess(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo populateActivityFailure(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo populateActivityUnderProcessing(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo getTotalActivityForPieChart(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo badgePopulation(SearchPojo searchPojo) throws ParseException;
    SearchPojo populateClients(SearchPojo searchPojo);
    SearchPojo populateOverAllClientActivity(SearchPojo searchPojo) throws ParseException;
    SearchPojo populateFailedClientActivities(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo populateSuccessfulActivitiesForParticularClient(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo populateTotalClientActivities(SearchPojo searchPojo) throws InvalidDateFormatException;
    SearchPojo populateUnderProcessingActivities(SearchPojo searchPojo) throws InvalidDateFormatException;
}

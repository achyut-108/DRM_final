package com.mJunction.drm.service;

import com.mJunction.drm.pojo.DashboardPojo;

/**
 * Created by siddhartha.kumar on 4/5/2017.
 */
public interface DashboardService {

    public DashboardPojo prepopulation(DashboardPojo dashboardPojo);
    public DashboardPojo fetchErrorStack(DashboardPojo dashboardPojo);
    DashboardPojo populateParticularFailedActivity(DashboardPojo dashboardPojo);
    DashboardPojo populateParticularSuccessfulActivity(DashboardPojo dashboardPojo);
    DashboardPojo populateParticularTotalActivity(DashboardPojo dashboardPojo);
    DashboardPojo populateParticularUnderActivity(DashboardPojo dashboardPojo);
}

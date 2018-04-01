package com.mJunction.drm.serviceImpl;

import com.mJunction.drm.common.FormatDate;
import com.mJunction.drm.common.PropertyFileReaderService;
import com.mJunction.drm.dao.ErrorTableRepository;
import com.mJunction.drm.dao.MjReportRepository;
import com.mJunction.drm.dao.ProcessStateTableRepository;
import com.mJunction.drm.pojo.ActivityGraphData;
import com.mJunction.drm.pojo.GraphPieChartPojo;
import com.mJunction.drm.service.GraphPieChartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by siddhartha.kumar on 4/12/2017.
 */

@Service
public class GraphPieChartServiceImpl implements GraphPieChartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphPieChartServiceImpl.class);

    @Autowired
    private PropertyFileReaderService propertyFileReaderService;
    @Autowired
    private ProcessStateTableRepository processStateTableRepository;
    @Autowired
    private MjReportRepository mjReportRepository;
    @Autowired
    private ErrorTableRepository errorTableRepository;

    @Override
    public GraphPieChartPojo populateSuccessActivities(GraphPieChartPojo graphPieChartPojo){

        List<ActivityGraphData> graphPlotActivityListSuccess = new ArrayList<>();

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateSucessActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateSucessActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("graphdateformat"));

        while (!localStartDate.isEqual(localEndDate)) {
//            String query = "select( SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = 'Processed' and cast(TIMESTAMP as date)='"
//                    + locDates
//                    + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = 'Processed' and cast(DateTimeStamp as date)='"
//                    + locDates + "') as sumcount";

            Integer countFromProcessStateTable = this.processStateTableRepository
                    .getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,"Processed");
            Integer countFromMjReportTable = this.mjReportRepository
                    .getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,"Processed");
            ActivityGraphData activityGraphData = new ActivityGraphData();
            activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable);
            activityGraphData.setAllDates(startDateSystem);
            graphPlotActivityListSuccess.add(activityGraphData);

            localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
            dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
            startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault())
                    .toInstant()), prop.getProperty("graphdateformat"));
        }

        graphPieChartPojo.setSuccessActivityGraphDataList(graphPlotActivityListSuccess);

        return graphPieChartPojo;
    }

    @Override
    public GraphPieChartPojo populateFailedActivities(GraphPieChartPojo graphPieChartPojo){

        List<ActivityGraphData> graphPlotActivityListFailure = new ArrayList<>();

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateFailedActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateFailedActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("graphdateformat"));


        while (!localStartDate.isEqual(localEndDate)) {
//            String query = "select( SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = 'Failure' and cast(TIMESTAMP as date)='"
//                    + locDates
//                    + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = 'Failure' and cast(DateTimeStamp as date)='"
//                    + locDates
//                    + "') + (SELECT COUNT(Date) FROM error_table WHERE CAST(Date AS DATE) ='"
//                    + locDates + "')  as sumcount";

            Integer countFromProcessStateTable = this.processStateTableRepository
                    .getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,"Failure");
            Integer countFromMjReportTable = this.mjReportRepository
                    .getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,"Failure");
            Integer countFromErrorTable = this.errorTableRepository.getCountByDate(dateAfterSubstractingDays);

            ActivityGraphData activityGraphData = new ActivityGraphData();
            activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable + countFromErrorTable);
            activityGraphData.setAllDates(startDateSystem);
            graphPlotActivityListFailure.add(activityGraphData);

            localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
            dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
            startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault())
                    .toInstant()), prop.getProperty("graphdateformat"));
        }

        graphPieChartPojo.setFailedActivityGraphDataList(graphPlotActivityListFailure);

        return graphPieChartPojo;
    }

    @Override
    public GraphPieChartPojo populateUnderProcessingActivities(GraphPieChartPojo graphPieChartPojo){
        List<ActivityGraphData> graphPlotActivityListSuccess = new ArrayList<>();

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateUnderProcessingActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateUnderProcessingActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("graphdateformat"));

        while (!localStartDate.isEqual(localEndDate)) {
//            String query = "select(SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = '' and cast(TIMESTAMP as date)='"
//                    + locDates
//                    + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = '' and cast(DateTimeStamp as date)='"
//                    + locDates + "') as sumcount";

            Integer countFromProcessStateTable = this.processStateTableRepository
                    .getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,"");
            Integer countFromMjReportTable = this.mjReportRepository
                    .getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,"");
            ActivityGraphData activityGraphData = new ActivityGraphData();
            activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable);
            activityGraphData.setAllDates(startDateSystem);
            graphPlotActivityListSuccess.add(activityGraphData);

            localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
            dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
            startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault())
                    .toInstant()), prop.getProperty("graphdateformat"));
        }

        graphPieChartPojo.setUnderProcessingActivityGraphDataList(graphPlotActivityListSuccess);

        return graphPieChartPojo;
    }

    @Override
    public GraphPieChartPojo getCountOfSuccessfulActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo){
        
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        String endDateSystem = FormatDate.dateToCustomformatString(date,prop.getProperty("dbdateformat"));

        LOGGER.info("[populateSucessActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateSucessActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("dbdateformat"));
//        String query = "SELECT (SELECT COUNT(timestamp) FROM process_state_table WHERE  Final_status = 'Processed' and cast(timestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "')+(SELECT COUNT(DateTimeStamp) from mj_report WHERE Final_status = 'Processed' and cast(DateTimeStamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "') AS SumCount";

        Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"Processed");
        Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"Processed");

        graphPieChartPojo.setTotalSuccesfulActivitiesForPieChart(countFromProcessStateTable + countFromMjReportTable);

        return graphPieChartPojo;
    }

    @Override
    public GraphPieChartPojo getCountOfFailedActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo){

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        String endDateSystem = FormatDate.dateToCustomformatString(date,prop.getProperty("dbdateformat"));

        LOGGER.info("[populateSucessActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateSucessActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("dbdateformat"));
//        String query = "SELECT (SELECT COUNT(timestamp) FROM process_state_table WHERE  Final_status = 'Failure' and cast(timestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "')+(SELECT COUNT(DateTimeStamp) from mj_report WHERE Final_status = 'Failure' and cast(DateTimeStamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "') +(SELECT COUNT(Date) from error_table WHERE cast(Date as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "') AS SumCount";

        Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"Failure");
        Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"Failure");
        Integer countFromErrorTable = this.errorTableRepository.getCountByDateColumn(dateAfterSubstractingDays,date);

        graphPieChartPojo.setTotalFailedActivitiesForPieChart(countFromProcessStateTable + countFromMjReportTable + countFromErrorTable);

        return graphPieChartPojo;
    }

    @Override
    public GraphPieChartPojo getCountOfUnderProcessingActivitiesForPieChart(GraphPieChartPojo graphPieChartPojo){

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime localEndDate = LocalDateTime.now();
        Date date = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

        String endDateSystem = FormatDate.dateToCustomformatString(date,prop.getProperty("dbdateformat"));

        LOGGER.info("[populateSucessActivities] : date at this instant :" + date);

        LocalDateTime localStartDate = localEndDate.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
        LOGGER.info("[populateSucessActivities] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("dbdateformat"));
        String query = "SELECT (SELECT COUNT(timestamp) FROM process_state_table WHERE  Final_status = '' and cast(timestamp as date) between '"
                + endDateSystem
                + "' and '"
                + startDateSystem
                + "')+(SELECT COUNT(DateTimeStamp) from mj_report WHERE Final_status = '' and cast(DateTimeStamp as date) between '"
                + endDateSystem
                + "' and '"
                + startDateSystem
                + "') AS SumCount";

        Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"");
        Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(dateAfterSubstractingDays,date,"");

        graphPieChartPojo.setTotalUnderProcessingActivitiesForPieChart(countFromProcessStateTable + countFromMjReportTable);
        return graphPieChartPojo;
    }
}

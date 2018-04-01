package com.mJunction.drm.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mJunction.drm.common.FormatDate;
import com.mJunction.drm.common.PropertyFileReaderService;
import com.mJunction.drm.common.entity.ErrorTable;
import com.mJunction.drm.common.entity.MjReport;
import com.mJunction.drm.common.entity.ProcessStateTable;
import com.mJunction.drm.common.exception.InvalidDateFormatException;
import com.mJunction.drm.common.validation.ErrorCodes;
import com.mJunction.drm.dao.ErrorTableRepository;
import com.mJunction.drm.dao.MjReportRepository;
import com.mJunction.drm.dao.ProcessStateTableRepository;
import com.mJunction.drm.pojo.ActivityGraphData;
import com.mJunction.drm.pojo.DataTableObject;
import com.mJunction.drm.pojo.SearchPojo;
import com.mJunction.drm.service.SearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by siddhartha.kumar on 4/7/2017.
 */

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Value("${imgUrlSuccess}")
    private String imgUrlSuccess;
    @Value("${imgUrlFail}")
    private String imgUrlFail;
    @Value("${imgUrlUnder}")
    private String imgUrlUnder;
    @Value("${underProcessing}")
    private String underProcessing;

    @Autowired
    private PropertyFileReaderService propertyFileReaderService;
    @Autowired
    private ProcessStateTableRepository processStateTableRepository;
    @Autowired
    private MjReportRepository mjReportRepository;
    @Autowired
    private ErrorTableRepository errorTableRepository;

    @Override
    public SearchPojo populateActivitySuccess(SearchPojo searchPojo) throws InvalidDateFormatException {
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateActivitySuccess] : date at this instant :" + date);

        try {

            String startDateSystem = FormatDate.formatStringToString(searchPojo.getFromDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));
            String endDateSystem = FormatDate.formatStringToString(searchPojo.getToDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));

            LocalDateTime localStartDate = FormatDate.StringToDate(startDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime localEndDate = FormatDate.StringToDate(endDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


//            LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
//            Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

            LocalDateTime localDateTime = LocalDateTime.parse(startDateSystem);
            List<ActivityGraphData> graphPlotActivityListSuccess = new ArrayList<ActivityGraphData>();

            Date startDate  = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localEndDate.atZone(ZoneId.systemDefault()).toInstant());

            while (!localStartDate.isEqual(localEndDate)) {
                Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate, "Processed", searchPojo.getClientName());

                Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate, "Processed", searchPojo.getClientName());

                ActivityGraphData activityGraphData = new ActivityGraphData();
                activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable);
                activityGraphData.setAllDates(startDateSystem);

                graphPlotActivityListSuccess.add(activityGraphData);

                localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
                startDate  = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
                startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant()), prop.getProperty("dbdateformat"));

//            String query = "select(SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = 'Processed' and client_name = '"
//                    + clientName
//                    + "' and cast(TIMESTAMP as date)='"
//                    + locDates
//                    + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = 'Processed' and Client_Name= '"
//                    + clientName
//                    + "' and cast(DateTimeStamp as date)='"
//                    + locDates + "') as sumcount";
            }

            Gson gson = new Gson();
            searchPojo.setJsonStringForSuccess(gson.toJson(graphPlotActivityListSuccess));

        }catch(ParseException pexcep){
            LOGGER.error("[populateActivitySuccess] : Exception : ",pexcep);
            throw new InvalidDateFormatException("Date is not valid");
        }
        return searchPojo;
    }

    @Override
    public SearchPojo populateActivityFailure(SearchPojo searchPojo) throws InvalidDateFormatException{
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateActivityFailure] : date at this instant :" + date);

    try{

            String startDateSystem = FormatDate.formatStringToString(searchPojo.getFromDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));
            String endDateSystem = FormatDate.formatStringToString(searchPojo.getToDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));

            LocalDateTime localStartDate = FormatDate.StringToDate(startDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime localEndDate = FormatDate.StringToDate(endDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime localDateTime = LocalDateTime.parse(startDateSystem);
            List<ActivityGraphData> graphPlotActivityListSuccess = new ArrayList<ActivityGraphData>();

            Date startDate  = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());

            while (!localStartDate.isEqual(localEndDate)) {

//                String query = "select( SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = 'Failure' and client_name = '"
//                        + clientName
//                        + "' and cast(TIMESTAMP as date)='"
//                        + locDates
//                        + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = 'Failure' and Client_Name= '"
//                        + clientName
//                        + "' and cast(DateTimeStamp as date)='"
//                        + locDates
//                        + "') + (SELECT COUNT(Date) FROM error_table WHERE Client_Name= '"
//                        + clientName
//                        + "' and cast(Date as date)='"
//                        + locDates
//                        + "') as sumcount";
//            }

                Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate, "Failure", searchPojo.getClientName());

                Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate, "Failure", searchPojo.getClientName());

                Integer countFromErrorTable = this.errorTableRepository.getCountByDateAndClientName(startDate, searchPojo.getClientName());

                ActivityGraphData activityGraphData = new ActivityGraphData();
                activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable + countFromErrorTable);
                activityGraphData.setAllDates(startDateSystem);

                graphPlotActivityListSuccess.add(activityGraphData);

                localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
                startDate = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
                startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant()), prop.getProperty("dbdateformat"));

                Gson gson = new Gson();
                searchPojo.setJsonStringForFailure(gson.toJson(graphPlotActivityListSuccess));

            }
    } catch(ParseException pexcep){
        LOGGER.error("[populateActivityFailure] : Exception : ", pexcep);
        throw new InvalidDateFormatException("Date is not valid");
    }

    return searchPojo;

    }

    @Override
    public SearchPojo populateActivityUnderProcessing(SearchPojo searchPojo) throws InvalidDateFormatException{
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateActivityUnderProcessing] : date at this instant :" + date);

        try {

            String startDateSystem = FormatDate.formatStringToString(searchPojo.getFromDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));
            String endDateSystem = FormatDate.formatStringToString(searchPojo.getToDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));

            LocalDateTime localStartDate = FormatDate.StringToDate(startDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime localEndDate = FormatDate.StringToDate(endDateSystem, prop.getProperty("dbdateformat")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            LocalDateTime localDateTime = LocalDateTime.parse(startDateSystem);
            List<ActivityGraphData> graphPlotActivityListUnderProcessing = new ArrayList<ActivityGraphData>();

            Date startDate  = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());

            while (!localStartDate.isEqual(localEndDate)) {
//                String query = "select( SELECT COUNT(TIMESTAMP) FROM process_state_table WHERE Final_status = '' and client_name = '"
//                        + clientName
//                        + "' and cast(TIMESTAMP as date)='"
//                        + locDates
//                        + "') + (SELECT COUNT(DateTimeStamp) FROM mj_report WHERE Final_status = '' and Client_Name= '"
//                        + clientName
//                        + "' and cast(DateTimeStamp as date)='"
//                        + locDates + "') as sumcount";
                Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate, "", searchPojo.getClientName());

                Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate, "", searchPojo.getClientName());

                ActivityGraphData activityGraphData = new ActivityGraphData();
                activityGraphData.setNoOfActivities(countFromProcessStateTable + countFromMjReportTable);
                activityGraphData.setAllDates(startDateSystem);

                graphPlotActivityListUnderProcessing.add(activityGraphData);

                localStartDate = localStartDate.plus(1, ChronoUnit.DAYS);
                startDate = Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant());
                startDateSystem = FormatDate.dateToCustomformatString(Date.from(localStartDate.atZone(ZoneId.systemDefault()).toInstant()), prop.getProperty("dbdateformat"));
            }

            Gson gson = new Gson();
            searchPojo.setJsonStringForUnderProcessing(gson.toJson(graphPlotActivityListUnderProcessing));

        }catch(ParseException pexcep){
            LOGGER.error("[populateActivityUnderProcessing] : Exception : ",pexcep);
            throw new InvalidDateFormatException("Date is not valid");
        }
        return searchPojo;
    }

    @Override
    public SearchPojo getTotalActivityForPieChart(SearchPojo searchPojo) throws InvalidDateFormatException {
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[populateActivityUnderProcessing] : date at this instant :" + date);

        try {

            String startDateSystem = FormatDate.formatStringToString(searchPojo.getFromDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));
            String endDateSystem = FormatDate.formatStringToString(searchPojo.getToDate(), "MM/dd/yyyy", prop.getProperty("dbdateformat"));

            Date startDate = FormatDate.StringToDate(startDateSystem,prop.getProperty("dbdateformat"));
            Date endDate = FormatDate.StringToDate(endDateSystem,prop.getProperty("dbdateformat"));

////                String query = "SELECT (SELECT COUNT(distinct p.timestamp) FROM process_state_table p WHERE  p.Final_status = 'Processed' and p.client_name = '"
//            + clientName
//                    + "'  and cast(p.timestamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "')+(SELECT COUNT(distinct m.DateTimeStamp) from mj_report m WHERE m.Final_status = 'Processed' and m.Client_Name= '"
//                    + clientName
//                    + "' and cast(m.datetimestamp as date) between '"
//                    + startDate + "' and '" + endDate + "') AS SumCount";
                double countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate, endDate, "Processed",searchPojo.getClientName());

                double countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate, endDate,"Processed", searchPojo.getClientName());

                double graphPlotPie = countFromProcessStateTable + countFromMjReportTable;

                countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"Failure",searchPojo.getClientName());
                countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate,endDate,"Failure",searchPojo.getClientName());

                double populateFailureListPieChart = countFromProcessStateTable + countFromMjReportTable;

//            String query = "SELECT (SELECT COUNT(DISTINCT p.timestamp) FROM process_state_table p WHERE  p.Final_status = '' and p.client_name = '"
//                    + clientName
//                    + "'  and cast(p.timestamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "')+(SELECT COUNT(m.DateTimeStamp) from mj_report m WHERE m.Final_status = '' and m.Client_Name= '"
//                    + clientName
//                    + "' and cast(m.datetimestamp as date) between '"
//                    + startDate + "' and '" + endDate + "') AS SumCount";

                countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"",searchPojo.getClientName());
                countFromMjReportTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"",searchPojo.getClientName());

            double populateUnderProcessListPieChart = countFromProcessStateTable + countFromMjReportTable;

            double imaginaryMark = 100.0;
            double accutualCount = 0;

            double countTotal = graphPlotPie + populateFailureListPieChart
                    + populateUnderProcessListPieChart;
            double imaginarySucess = (graphPlotPie * imaginaryMark / countTotal);
            double imaginaryFailure = (populateFailureListPieChart * imaginaryMark / countTotal);
            double imaginaryUnder = (populateUnderProcessListPieChart * imaginaryMark / countTotal);

            double imaginarySucessAfterRound=Math.round(imaginarySucess * 100);
            double imaginaryFailureAfterRound=Math.round(imaginaryFailure * 100);
            double imaginaryUnderAfterRound=Math.round(imaginaryUnder * 100);

            imaginarySucessAfterRound = imaginarySucessAfterRound / 100;
            imaginaryFailureAfterRound = imaginaryFailureAfterRound / 100;
            imaginaryUnderAfterRound = imaginaryUnderAfterRound / 100;

            double totalImaginaryCount = (imaginarySucessAfterRound + imaginaryFailureAfterRound + imaginaryUnderAfterRound);

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

            Object[] arr1 = { new String("Successful Activity"),new Double(graphPlotPie)};

            Object[] arr2 = {new String("Failed Activity"),new Double(populateFailureListPieChart)};

            Object[] arr3 = {new String("Under Processing Activity"),new Double(populateUnderProcessListPieChart)};

            Object finalStringReturn[][] = { arr1, arr2, arr3 };

            searchPojo.setJsonStringForPieChart(gson.toJson(finalStringReturn));

        }catch(ParseException pexcep){
            LOGGER.error("[populateActivityUnderProcessing] : Exception : ",pexcep);
            throw new InvalidDateFormatException("Date is not valid");
        }

        return searchPojo;
    }

    @Override
    public SearchPojo badgePopulation(SearchPojo searchPojo) throws ParseException {

        if(Objects.isNull(searchPojo.getAuthTokenHtml())){
            searchPojo.addValidationError(ErrorCodes.INVALID_DATA.getCode(),"error","","");
            return searchPojo;
        }
        Properties prop = this.propertyFileReaderService.getProperty();

        String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(),
                prop.getProperty("searchdateformat"),prop.getProperty("dbdateformat"));
        String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(),
                prop.getProperty("searchdateformat"),prop.getProperty("dbdateformat"));

        Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
        Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));


        if(searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())
                && ("N".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb())
                || "Y".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb()))){

//            String queryunderProcessing = "SELECT (SELECT COUNT(*) FROM process_state_table p WHERE  p.Final_status = '' and p.client_name='"
//                    + clientName
//                    + "' and cast(p.timestamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "')+(SELECT COUNT(*) from mj_report m WHERE m.Final_status = '' and m.client_name='"
//                    + clientName
//                    + "' and cast(m.DateTimeStamp as date) between '"
//                    + startDate + "' and '" + endDate + "') AS SumCount";

            Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"",searchPojo.getClientName());
            Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate,endDate,"",searchPojo.getClientName());

            Integer underProcessingActivities = countFromProcessStateTable + countFromMjReportTable;

//            String querySuccessful = "SELECT (SELECT COUNT(*) FROM process_state_table p WHERE  p.Final_status = 'Processed' and p.client_name='"
//                    + clientName
//                    + "' and cast(p.timestamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "')+(SELECT COUNT(*) from mj_report m WHERE m.Final_status = 'Processed' and m.client_name='"
//                    + clientName
//                    + "' and cast(m.DateTimeStamp as date) between '"
//                    + startDate + "' and '" + endDate + "')  AS SumCount";

            countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"Processed",searchPojo.getClientName());
            countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate,endDate,"Processed",searchPojo.getClientName());

            Integer totalSuccessfulActivities = countFromProcessStateTable + countFromMjReportTable;

//            String queryFailed = "SELECT (SELECT COUNT(*) FROM process_state_table p WHERE  p.Final_status = 'Failure' and p.client_name='"
//                    + clientName
//                    + "' and cast(p.timestamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "')+(SELECT COUNT(*) from mj_report m WHERE m.Final_status = 'Failure' and m.client_name='"
//                    + clientName
//                    + "' and cast(m.DateTimeStamp as date) between '"
//                    + startDate
//                    + "' and '"
//                    + endDate
//                    + "') +(SELECT COUNT(*) FROM error_table e WHERE e.client_name='"
//                    + clientName
//                    + "' and CAST(e.Date AS DATE) BETWEEN '"
//                    + startDate + "' and '" + endDate + "') AS SumCount";

            countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatusAndClientName(startDate,endDate,"Failure",searchPojo.getClientName());
            countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatusAndClientName(startDate,endDate,"Failure",searchPojo.getClientName());
            Integer countFromErrorTable = this.errorTableRepository.getCountByDateAndClientName(startDate,endDate,searchPojo.getClientName());

            Integer failedActivities = countFromProcessStateTable + countFromMjReportTable + countFromErrorTable;

            Map<String, String> options = new LinkedHashMap<>();
            options.put("totalActivities", String.valueOf(underProcessingActivities + totalSuccessfulActivities + failedActivities));
            options.put("underProcessing", String.valueOf(underProcessingActivities));
            options.put("successfulActivities", String.valueOf(totalSuccessfulActivities));
            options.put("failedActivities", String.valueOf(failedActivities));

            searchPojo.setJsonStringForBadgePopulation(new Gson().toJson(options));
        }

        return searchPojo;
    }

    @Override
    public SearchPojo populateClients(SearchPojo searchPojo){
       // "SELECT distinct (client_name) FROM m_junction.process_state_table"
        searchPojo.setJsonStringForListOfClients(new Gson().toJson(this.processStateTableRepository.getListOfClients()));
        return searchPojo;
    }

    @Override
    public SearchPojo populateOverAllClientActivity(SearchPojo searchPojo) throws ParseException {

        Properties prop = this.propertyFileReaderService.getProperty();


        if(!searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())){
            searchPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"","");
            return searchPojo;
        }

        String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(),
                prop.getProperty("searchnewdateformat"),prop.getProperty("dbdateformat"));
        String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(),
                prop.getProperty("searchnewdateformat"),prop.getProperty("dbdateformat"));

        Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
        Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));

//        String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p where p.Final_status='Failure'  and p.client_name='"
//                + clientName
//                + "' and cast(p.timestamp as date) between '"
//                + startDate
//                + "' and '"
//                + endDate
//                + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m where m.Final_status='Failure' and m.client_name='"
//                + clientName
//                + "' and cast(m.datetimestamp as date) between '"
//                + startDate
//                + "' and '"
//                + endDate
//                + "'"
//                + "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where e.client_name='"
//                + clientName
//                + "' and cast(e.Date as date) between '"
//                + startDate + "' and '" + endDate + "' order by 4 desc,2";


        List<ProcessStateTable> processStateTableList = this.processStateTableRepository.findAllByTimeStampAndFinalStatusAndClientName
                (startDate, endDate,"Failure",searchPojo.getClientName());
        List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatusAndClientName
                (startDate, endDate,"Failure",searchPojo.getClientName());
        List<ErrorTable> errorTableList = this.errorTableRepository.findAllByDateAndClientName
                (startDate,endDate,searchPojo.getClientName());

        List<com.mJunction.drm.pojo.Client> listOfTotalClients = new ArrayList<>();

        processStateTableList.stream().forEach(e -> {
            com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            if(e.getFinalStatus() == null || e.getFinalStatus().trim().isEmpty()){
                e.setFinalStatus(underProcessing);
            }
            String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
            client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setStatus(imgUrlFail);//since final status will always be failure
            listOfTotalClients.add(client1);
        });

        mjReportList.stream().forEach(e -> {
            com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
            client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setStatus(imgUrlFail);//since final status will always be failure
            listOfTotalClients.add(client1);
        });

        errorTableList.stream().forEach(e -> {
            com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
            client1.setActivity(e.getErrorDescription());
            client1.setClient(e.getClient_name());

            String dateString = FormatDate.dateToCustomformatString(e.getId().getDate(), "dd-MM-yyyy HH:mm:ss");
            client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + "Failure");
            client1.setStatus(imgUrlFail);//since final status will always be failure
            listOfTotalClients.add(client1);
        });


        com.mJunction.drm.pojo.DataTableObject dataTableObject = new com.mJunction.drm.pojo.DataTableObject();

        dataTableObject.setAaData(listOfTotalClients );

        searchPojo.setJsonStringForOverAllClientActivity(new GsonBuilder().setPrettyPrinting().create().toJson(dataTableObject));

        return searchPojo;
    }


    @Override
    public SearchPojo populateFailedClientActivities(SearchPojo searchPojo) throws InvalidDateFormatException {
        Properties prop = this.propertyFileReaderService.getProperty();
        if(!searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())){
            searchPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return searchPojo;
        }

        try {
            String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));
            String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));

            Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
            Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));


            if("Y".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb()) || "N".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb())) {
//                String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p where p.Final_status='Failure' and p.client_name='"
//                        + clientName
//                        + "' and cast(p.timestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m where m.Final_status='Failure' and m.client_name='"
//                        + clientName
//                        + "' and cast(m.datetimestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "'"
//                        + " UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where e.client_name='"
//                        + clientName
//                        + "' and cast(e.Date as date) between '"
//                        + startDate + "' and '" + endDate + "' order by 4 desc,2";

                List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                        findAllByTimeStampAndFinalStatusAndClientName(startDate, endDate, "Failure", searchPojo.getClientName());
                List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatusAndClientName(startDate, endDate, "Failure", searchPojo.getClientName());
                List<ErrorTable> errorTableList = this.errorTableRepository.findAllByDateAndClientName(startDate, endDate, searchPojo.getClientName());

                List<com.mJunction.drm.pojo.Client> listOfTotalClients = new ArrayList<>();

                processStateTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getActivity() + "~" + e.getId().getType());
                    client1.setClient(e.getClientName());
                    String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlFail);//since final status will always be failure
                    listOfTotalClients.add(client1);
                });

                mjReportList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getActivity() + "~" + e.getId().getType());
                    client1.setClient(e.getClientName());

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlFail);//since final status will always be failure
                    listOfTotalClients.add(client1);
                });

                errorTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" +e.getErrorDescription());
                    client1.setClient(e.getClient_name());

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDate(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + "Failure");
                    client1.setStatus(imgUrlFail);//since final status will always be failure
                    listOfTotalClients.add(client1);
                });


                com.mJunction.drm.pojo.DataTableObject dataTableObject = new com.mJunction.drm.pojo.DataTableObject();
                dataTableObject.setAaData(listOfTotalClients);

                searchPojo.setJsonStringForFailedClientActivities(new GsonBuilder().setPrettyPrinting().create().toJson(dataTableObject));
            }
        }catch(ParseException idfexcep){
            throw new InvalidDateFormatException("date format is not valid");
        }

        return searchPojo;

    }

    @Override
    public SearchPojo populateSuccessfulActivitiesForParticularClient(SearchPojo searchPojo) throws InvalidDateFormatException {

        Properties prop = this.propertyFileReaderService.getProperty();
        if(!searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())){
            searchPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return searchPojo;
        }

        try {
            String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));
            String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));

            Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
            Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));

            if ("Y".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb()) || "N".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb())) {
//                String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p " +
//                        "where p.Final_status='Processed' and p.client_name='"
//                        + clientName
//                        + "' and cast(p.timestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "'  UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m " +
//                        "where m.Final_status='Processed' and m.client_name='"
//                        + clientName
//                        + "' and cast(m.datetimestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "' order by 4 desc,2 ";
                List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                        findAllByTimeStampAndFinalStatusAndClientName(startDate, endDate, "Proesessed", searchPojo.getClientName());
                List<MjReport> mjReportList = this.mjReportRepository.
                        findAllByDateTimeStampAndFinalStatusAndClientName(startDate, endDate, "Failure", searchPojo.getClientName());

                List<com.mJunction.drm.pojo.Client> listOfTotalClients = new ArrayList<>();

                processStateTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());
                    String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlSuccess);//since final status will always be Processed
                    listOfTotalClients.add(client1);
                });

                mjReportList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlSuccess);//since final status will always be Processed
                    listOfTotalClients.add(client1);
                });

                com.mJunction.drm.pojo.DataTableObject dataTableObject = new com.mJunction.drm.pojo.DataTableObject();
                dataTableObject.setAaData(listOfTotalClients);

                searchPojo.setJsonStringForSuccessfulClientActivities(new GsonBuilder().setPrettyPrinting().create().toJson(dataTableObject));

            }

        }catch(ParseException pexcep){
            LOGGER.error("[populateSuccessfulActivitiesForParticularClient] : Exception",pexcep);
            throw new InvalidDateFormatException("Date Format is not valid!!");
        }

        return  searchPojo;
    }

    @Override
    public SearchPojo populateTotalClientActivities(SearchPojo searchPojo) throws InvalidDateFormatException {

        Properties prop = this.propertyFileReaderService.getProperty();
        if(!searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())){
            searchPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return searchPojo;
        }

        try {
            String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));
            String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));

            Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
            Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));

            if ("Y".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb()) ||
                    "N".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb())) {

                List<com.mJunction.drm.pojo.Client> listOfTotalClients = new ArrayList<>();

//                String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p where p.client_name='"
//                        + clientName
//                        + "' and cast(p.timestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m where m.client_name='"
//                        + clientName
//                        + "' and cast(m.datetimestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "'"
//                        + "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where e.client_name='"
//                        + clientName
//                        + "' and cast(e.Date as date) between '"
//                        + startDate + "' and '" + endDate + "' order by 4 desc,2";
                List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                        findAllByTimestampAndClientName(startDate, endDate, searchPojo.getClientName());
                List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimestampAndClientName(startDate, endDate, searchPojo.getClientName());
                List<ErrorTable> errorTableList = this.errorTableRepository.findAllByDateAndClientName(startDate, endDate, searchPojo.getClientName());

                processStateTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());

                    if (e.getFinalStatus() == null || e.getFinalStatus().trim().isEmpty()) {
                        e.setFinalStatus(underProcessing);
                    }
                    String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    if ("Processed".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlSuccess);
                    else if ("Failure".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlFail);
                    else client1.setStatus(imgUrlUnder);
                    listOfTotalClients.add(client1);
                });

                mjReportList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());

                    if (e.getFinalStatus() == null || e.getFinalStatus().trim().isEmpty()) {
                        e.setFinalStatus(underProcessing);
                    }

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    if ("Processed".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlSuccess);
                    else if ("Failure".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlFail);
                    else client1.setStatus(imgUrlUnder);
                    listOfTotalClients.add(client1);
                });

                errorTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getErrorDescription());
                    client1.setClient(e.getClient_name());

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDate(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + "Failure");
                    client1.setStatus(imgUrlFail);//since final status will always be failure
                    listOfTotalClients.add(client1);
                });

                DataTableObject dataTableObject = new DataTableObject();
                dataTableObject.setAaData(listOfTotalClients);

                searchPojo.setJsonStringForTotalClientActivities(new GsonBuilder().setPrettyPrinting().create()
                        .toJson(dataTableObject));
            }
        }catch (ParseException e){
            LOGGER.error("[populateTotalClientActivities] : Exception :",e);
            throw new InvalidDateFormatException("Date Format is not valid!!");
        }

        return  searchPojo;
    }


    @Override
    public SearchPojo populateUnderProcessingActivities(SearchPojo searchPojo) throws InvalidDateFormatException{
        Properties prop = this.propertyFileReaderService.getProperty();
        if(!searchPojo.getAuthTokenHtml().equalsIgnoreCase(searchPojo.getInSessionAuthToken())){
            searchPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return searchPojo;
        }

        try {
            String startDateString = FormatDate.formatStringToString(searchPojo.getFromDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));
            String endDateString = FormatDate.formatStringToString(searchPojo.getToDate(), prop.getProperty("searchnewdateformat"), prop.getProperty("dbdateformat"));

            Date startDate = FormatDate.StringToDate(startDateString,prop.getProperty("dbdateformat"));
            Date endDate = FormatDate.StringToDate(endDateString,prop.getProperty("dbdateformat"));

            if ("Y".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb()) || "N".equalsIgnoreCase(searchPojo.getCheckWebServiceOrDb())) {
////                String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p where p.Final_status='' and p.client_name='"
//                + clientName
//                        + "' and cast(p.timestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m where m.Final_status='' and m.client_name='"
//                        + clientName
//                        + "' and cast(m.datetimestamp as date) between '"
//                        + startDate
//                        + "' and '"
//                        + endDate
//                        + "' order by 4 desc,2";
                List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                        findAllByTimeStampAndFinalStatusAndClientName(startDate, endDate, "", searchPojo.getClientName());
                List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatusAndClientName(startDate, endDate, "", searchPojo.getClientName());

                List<com.mJunction.drm.pojo.Client> listOfTotalClients = new ArrayList<>();

                processStateTableList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());
                    String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlUnder);//since final status will always be Under Processing
                    listOfTotalClients.add(client1);
                });

                mjReportList.stream().forEach(e -> {
                    com.mJunction.drm.pojo.Client client1 = new com.mJunction.drm.pojo.Client();
                    client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                    client1.setClient(e.getClientName());

                    String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                    client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                    client1.setStatus(imgUrlUnder);//since final status will always be Under Processing
                    listOfTotalClients.add(client1);
                });

                DataTableObject dataTableObject = new DataTableObject();
                dataTableObject.setAaData(listOfTotalClients);

                searchPojo.setJsonStringForUnderProcessingActivities(new GsonBuilder().setPrettyPrinting().create().toJson(dataTableObject));

            }

        }catch(ParseException pexcep){
            LOGGER.error("[populateSuccessfulActivitiesForParticularClient] : Exception",pexcep);
            throw new InvalidDateFormatException("Date Format is not valid!!");
        }
        return searchPojo;
    }

}

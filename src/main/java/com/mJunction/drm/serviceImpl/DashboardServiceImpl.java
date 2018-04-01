package com.mJunction.drm.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mJunction.drm.common.FormatDate;
import com.mJunction.drm.common.PropertyFileReaderService;
import com.mJunction.drm.common.entity.BuisnessErrorTable;
import com.mJunction.drm.common.entity.ErrorTable;
import com.mJunction.drm.common.entity.MjReport;
import com.mJunction.drm.common.entity.ProcessStateTable;
import com.mJunction.drm.common.validation.ErrorCodes;
import com.mJunction.drm.dao.BuisnessErrorTableRepository;
import com.mJunction.drm.dao.ErrorTableRepository;
import com.mJunction.drm.dao.MjReportRepository;
import com.mJunction.drm.dao.ProcessStateTableRepository;
import com.mJunction.drm.pojo.Client;
import com.mJunction.drm.pojo.DashboardPojo;
import com.mJunction.drm.pojo.DataTableObject;
import com.mJunction.drm.pojo.ReasonLog;
import com.mJunction.drm.service.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by siddhartha.kumar on 4/5/2017.
 */

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    private PropertyFileReaderService propertyFileReaderService;
    @Autowired
    private ProcessStateTableRepository processStateTableRepository;
    @Autowired
    private MjReportRepository mjReportRepository;
    @Autowired
    private ErrorTableRepository errorTableRepository;
    @Autowired
    private BuisnessErrorTableRepository buisnessErrorTableRepository;

    @Value("${imgUrlSuccess}")
    private String imgUrlSuccess;
    @Value("${imgUrlFail}")
    private String imgUrlFail;
    @Value("${imgUrlUnder}")
    private String imgUrlUnder;
    @Value("${underProcessing}")
    private String underProcessing;

    @Override
    public DashboardPojo prepopulation(DashboardPojo dashboardPojo){

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[prepopulation] : date at this instant :" + date);

        String endDateSystem = FormatDate.dateToCustomformatString(date,prop.getProperty("dbdateformat"));

        LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[prepopulation] : date after substracting 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays,prop.getProperty("dbdateformat"));

        LOGGER.info("[prepopulation] : String date after substracting 4 days : " + startDateSystem);

        if(Objects.isNull(dashboardPojo.getAuthTokenHtml())){
            dashboardPojo.addValidationError(ErrorCodes.INVALID_DATA.getCode(),"error","","");
            return dashboardPojo;
        }

        if(dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())
                && ("N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())
                || "Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb()))){

            this.prepopulationHelper(dashboardPojo,dateAfterSubstractingDays,date);
            return dashboardPojo;
        }

        //If there is any error
        dashboardPojo.addValidationError(ErrorCodes.INVALID_DATA.getCode(),"error","","");
        return dashboardPojo;
    }

    @Override
    public DashboardPojo fetchErrorStack(DashboardPojo dashboardPojo){

        if(!dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())){
            dashboardPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"","");
            return dashboardPojo;
        }


        if ("Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())) {

            List<Client> listOfClient = this.getClientFailedList();
            DataTableObject dataTableObject = new DataTableObject();
            dataTableObject.setAaData(listOfClient);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(dataTableObject));
            return dashboardPojo;
        }

        if("N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(this.getErrorStack(dashboardPojo)));
            return dashboardPojo;
        }

        //If there is any error
        dashboardPojo.addValidationError(ErrorCodes.INVALID_DATA.getCode(),"error","","");

        return dashboardPojo;
    }


    @Override
    public DashboardPojo populateParticularFailedActivity(DashboardPojo dashboardPojo){
        if(!dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())){
            dashboardPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"","");
            return dashboardPojo;
        }
        if ("Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb()) ||
                "N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())) {

            List<Client> listOfClient = this.getClientFailedList();
            DataTableObject dataTableObject = new DataTableObject();
            dataTableObject.setAaData(listOfClient);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(dataTableObject));
            return dashboardPojo;
        }


        return dashboardPojo;
    }

    @Override
    public DashboardPojo populateParticularSuccessfulActivity(DashboardPojo dashboardPojo){
        if(!dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())){
            dashboardPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return dashboardPojo;
        }

        if ("Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb()) ||
                "N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())) {

            List<Client> listOfClient = this.getClientSuccessfulList();
            DataTableObject dataTableObject = new DataTableObject();
            dataTableObject.setAaData(listOfClient);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(dataTableObject));
            return dashboardPojo;
        }


        return dashboardPojo;
    }

    @Override
    public DashboardPojo populateParticularTotalActivity(DashboardPojo dashboardPojo){
        if(!dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())){
            dashboardPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return dashboardPojo;
        }

        if ("Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb()) ||
                "N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())) {

            List<Client> listOfClient = this.getTotalClientList();
            DataTableObject dataTableObject = new DataTableObject();
            dataTableObject.setAaData(listOfClient);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(dataTableObject));
            return dashboardPojo;
        }

        return dashboardPojo;
    }

    @Override
    public DashboardPojo populateParticularUnderActivity(DashboardPojo dashboardPojo){
        if(!dashboardPojo.getAuthTokenHtml().equalsIgnoreCase(dashboardPojo.getInSessionAuthToken())){
            dashboardPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return dashboardPojo;
        }

        if ("Y".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb()) ||
                "N".equalsIgnoreCase(dashboardPojo.getCheckWebServiceOrDb())) {

            List<Client> listOfClient = this.listOfClientsUnderProcessing();
            DataTableObject dataTableObject = new DataTableObject();
            dataTableObject.setAaData(listOfClient);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dashboardPojo.setJsonOutput(gson.toJson(dataTableObject));
            return dashboardPojo;
        }

        return dashboardPojo;
    }

    /**
     * helper method to prepopulation
     * @param dashboardPojo
     * @param startDateSystem
     * @param endDateSystem
     */
    private void prepopulationHelper(DashboardPojo dashboardPojo,Date startDateSystem,Date endDateSystem){
        Integer countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(startDateSystem,endDateSystem,"");
        Integer countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(startDateSystem,endDateSystem,"");

        dashboardPojo.setUnderProcessingActivities(countFromProcessStateTable + countFromMjReportTable);

        countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(startDateSystem,endDateSystem,"Processed");
        countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(startDateSystem,endDateSystem,"Processed");

        dashboardPojo.setSuccessfulActivities(countFromProcessStateTable + countFromMjReportTable);

        countFromProcessStateTable = this.processStateTableRepository.getCountByTimeStampAndFinalStatus(startDateSystem,endDateSystem,"Failure");
        countFromMjReportTable = this.mjReportRepository.getCountByDateTimeStampAndFinalStatus(startDateSystem,endDateSystem,"Failure");

        Integer countFromErrorTable = this.errorTableRepository.getCountByDateColumn(startDateSystem,endDateSystem);

        dashboardPojo.setFailedActivities(countFromProcessStateTable + countFromMjReportTable + countFromErrorTable);

        dashboardPojo.setTotalActivities(dashboardPojo.getSuccessfulActivities() + dashboardPojo.getFailedActivities()
                + dashboardPojo.getUnderProcessingActivities());

        Map<String, String> options = new LinkedHashMap<>();
        options.put("totalActivities", dashboardPojo.getTotalActivities().toString());
        options.put("underProcessing", dashboardPojo.getUnderProcessingActivities().toString());
        options.put("successfulActivities", dashboardPojo.getSuccessfulActivities().toString());
        options.put("failedActivities", dashboardPojo.getFailedActivities().toString());

        dashboardPojo.setJsonOutput(new Gson().toJson(options));
        dashboardPojo.setValidationErrors(null);//since everything is ok

    }


    /**
     * helper method to fetchErrorStack
     * @return
     */
    public List<Client> getClientFailedList() {

            Properties prop = this.propertyFileReaderService.getProperty();

            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

            LOGGER.info("[getClientFailedList] : date at this instant :" + date);

            String endDateSystem = FormatDate.dateToCustomformatString(date, prop.getProperty("dbdateformat"));

            LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
            Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

            LOGGER.info("[getClientFailedList] : date after adding 4 days" + dateAfterSubstractingDays);

            String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays, prop.getProperty("dbdateformat"));

            LOGGER.info("[getClientFailedList] : String date after adding 4 days : " + startDateSystem);

//            String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p "
//                    + "where p.Final_status='Failure' and cast(p.timestamp as date) between '"
//                    + endDateSystem
//                    + "' and '"
//                    + startDateSystem
//                    + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m "
//                    + "where m.Final_status='Failure' and cast(m.datetimestamp as date) between '"
//                    + endDateSystem
//                    + "' and '"
//                    + startDateSystem
//                    + "'"
//                    + "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where cast(e.Date as date) between "
//                    + "'" + endDateSystem + "' and '" + startDateSystem + "' order by 4 desc,2";


            List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                    findAllByTimeStampAndFinalStatus(dateAfterSubstractingDays, date, "Failure");
            List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatus(dateAfterSubstractingDays, date, "Failure");
            List<ErrorTable> errorTableList = this.errorTableRepository.findAllByDateAndFinalStatus(dateAfterSubstractingDays, date);


            List<Client> listOfClientFailed = new ArrayList<>();

            processStateTableList.stream().forEach(e -> {
                Client client1 = new Client();
                client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                client1.setClient(e.getClientName());

                String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
//                client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                client1.setRecordDate(dateString + "_" + e.getFinalStatus());
                client1.setStatus(imgUrlFail);//since final status will always be failure
                listOfClientFailed.add(client1);
            });

            mjReportList.stream().forEach(e -> {
                Client client1 = new Client();
                client1.setActivity(e.getId().getType() + "~" + e.getActivity());
                client1.setClient(e.getClientName());

                String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
                //client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
                client1.setRecordDate(dateString + "_" + e.getFinalStatus());
                client1.setStatus(imgUrlFail);//since final status will always be failure
                listOfClientFailed.add(client1);
            });

            errorTableList.stream().forEach(e -> {
                Client client1 = new Client();
                client1.setActivity(e.getId().getType() + "~" +e.getErrorDescription());
                client1.setClient(e.getClient_name());

                String dateString = FormatDate.dateToCustomformatString(e.getId().getDate(), "dd-MM-yyyy HH:mm:ss");
                client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + "Failure");
                client1.setStatus(imgUrlFail);//since final status will always be failure
                listOfClientFailed.add(client1);
            });

        return listOfClientFailed;
    }

    public List<ReasonLog> getErrorStack(DashboardPojo dashboardPojo) {

//            String query = "SELECT m.Stage_state,m.Error_description  FROM m_junction.error_table m where m.Type='"
//                    + catCode
//
//                    + "' union SELECT b.Stage_state,b.Error_description  FROM m_junction.business_error_table b where b.Type='"
//                    + catCode + "' ";

            List<BuisnessErrorTable> buisnessErrorTableList = this.buisnessErrorTableRepository.findAllByType(dashboardPojo.getCatCode());

            List<ErrorTable> errorTableList = this.errorTableRepository.findAllByType(dashboardPojo.getCatCode());

            List<ReasonLog> listOfResponse = new ArrayList<ReasonLog>();

            if(Objects.nonNull(buisnessErrorTableList)){

                buisnessErrorTableList.stream().forEach(e->{
                    ReasonLog reasonLog = new ReasonLog();
                    reasonLog.setReason(e.getStageState() + ",");
                    reasonLog.setExceptionLog(e.getErrorDescription() + ",");
                    listOfResponse.add(reasonLog);
                });

            }

            if(Objects.nonNull(errorTableList)){

                errorTableList.stream().forEach(e->{
                    ReasonLog reasonLog = new ReasonLog();
                    reasonLog.setReason(e.getStageState() + ",");
                    reasonLog.setExceptionLog(e.getErrorDescription() + ",");
                    listOfResponse.add(reasonLog);
                });

            }
        return listOfResponse;
    }


    /**
     * Utility method to help populateParticularSuccessfulActivity
     * @return
     */
    public List<Client> getClientSuccessfulList() {

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[getClientSuccessfulList] : date at this instant :" + date);

        String endDateSystem = FormatDate.dateToCustomformatString(date, prop.getProperty("dbdateformat"));

        LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[getClientSuccessfulList] : date after adding 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays, prop.getProperty("dbdateformat"));

        LOGGER.info("[getClientSuccessfulList] : String date after adding 4 days : " + startDateSystem);

//        String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p "
//                + "where p.Final_status='Processed'  and cast(p.timestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m "
//                + "where m.Final_status='Processed' and cast(m.datetimestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "' order by 4 desc,2";
////					+ "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where cast(e.Date as date) between "
////					+ "'" + endDateSystem + "' and '" + startDateSystem + "'";


        List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                findAllByTimeStampAndFinalStatus(dateAfterSubstractingDays, date, "Processed");
        List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatus(dateAfterSubstractingDays, date, "Processed");


        List<Client> listOfClientSuccess = new ArrayList<>();

        processStateTableList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
            //client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            client1.setStatus(imgUrlSuccess);//since final status will always be Processed
            listOfClientSuccess.add(client1);
        });

        mjReportList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
            //client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            client1.setStatus(imgUrlSuccess);//since final status will always be Processed
            listOfClientSuccess.add(client1);
        });

        return listOfClientSuccess;
    }

    public List<Client> getTotalClientList(){
        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[getClientSuccessfulList] : date at this instant :" + date);

        String endDateSystem = FormatDate.dateToCustomformatString(date, prop.getProperty("dbdateformat"));

        LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[getClientSuccessfulList] : date after adding 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays, prop.getProperty("dbdateformat"));

        LOGGER.info("[getClientSuccessfulList] : String date after adding 4 days : " + startDateSystem);

//        String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p "
//                + " where cast(p.timestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "'"
//                + " UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m where cast(m.datetimestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "'"
//                + "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where cast(e.Date as date) between "
//                + "'"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "' order by 4 desc,2 ";

        List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                findByTimeStampBetween(dateAfterSubstractingDays, date);
        List<MjReport> mjReportList = this.mjReportRepository.findByDateTimeStampBetween(dateAfterSubstractingDays, date);
        List<ErrorTable> errorTableList = this.errorTableRepository.findByDateBetweenTwoDates(dateAfterSubstractingDays,date);

        List<Client> listOfTotalClients = new ArrayList<>();

        processStateTableList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            if(e.getFinalStatus() == null || e.getFinalStatus().trim().isEmpty()){
                e.setFinalStatus(underProcessing);
            }
            String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
//            client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            LOGGER.info("[getTotalClientList] : dateString : ",dateString);

            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            if("Processed".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlSuccess);
            else if("Failure".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlFail);
            else client1.setStatus(imgUrlUnder);
            listOfTotalClients.add(client1);
        });

        mjReportList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());

            if(e.getFinalStatus() == null || e.getFinalStatus().trim().isEmpty()){
                e.setFinalStatus(underProcessing);
            }

            String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
           // client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            if("Processed".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlSuccess);
            else if("Failure".equalsIgnoreCase(e.getFinalStatus())) client1.setStatus(imgUrlFail);
            else client1.setStatus(imgUrlUnder);
            listOfTotalClients.add(client1);
        });

        errorTableList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getErrorDescription());
            client1.setClient(e.getClient_name());

            String dateString = FormatDate.dateToCustomformatString(e.getId().getDate(), "dd-MM-yyyy HH:mm:ss");
           // client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + "Failure");
            client1.setRecordDate(dateString + "_" + "Failure");
            client1.setStatus(imgUrlFail);//since final status will always be failure
            listOfTotalClients.add(client1);
        });

        return listOfTotalClients;
    }

    public List<Client> listOfClientsUnderProcessing(){

        Properties prop = this.propertyFileReaderService.getProperty();

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[listOfClientsUnderProcessing] : date at this instant :" + date);

        String endDateSystem = FormatDate.dateToCustomformatString(date, prop.getProperty("dbdateformat"));

        LocalDateTime later = now.minus(4, ChronoUnit.DAYS);
        Date dateAfterSubstractingDays = Date.from(later.atZone(ZoneId.systemDefault()).toInstant());

        LOGGER.info("[listOfClientsUnderProcessing] : date after adding 4 days" + dateAfterSubstractingDays);

        String startDateSystem = FormatDate.dateToCustomformatString(dateAfterSubstractingDays, prop.getProperty("dbdateformat"));

        LOGGER.info("[listOfClientsUnderProcessing] : String date after adding 4 days : " + startDateSystem);

//        String query = "SELECT p.client_name,p.type,p.activity,p.timestamp,p.Final_status FROM process_state_table p"
//                + " where p.Final_status='' and cast(p.timestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "' UNION SELECT m.client_name,m.type,m.Activity,m.DateTimeStamp,m.Final_status FROM mj_report m "
//                + "where m.Final_status='' and cast(m.datetimestamp as date) between '"
//                + endDateSystem
//                + "' and '"
//                + startDateSystem
//                + "' order by 4 desc,2";
////					+ "UNION SELECT e.client_name,e.type,e.Error_description,e.Date,'Report' FROM error_table e where cast(e.Date as date) between "
////					+ "'" + endDateSystem + "' and '" + startDateSystem + "'";

        List<ProcessStateTable> processStateTableList = this.processStateTableRepository.
                findAllByTimeStampAndFinalStatus(dateAfterSubstractingDays, date,"");
        List<MjReport> mjReportList = this.mjReportRepository.findAllByDateTimeStampAndFinalStatus(dateAfterSubstractingDays, date,"");

        List<Client> listOfTotalClientsUnderProcessing = new ArrayList<>();

        processStateTableList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());
            String dateString = FormatDate.dateToCustomformatString(e.getId().getTimeStamp(), "dd-MM-yyyy HH:mm:ss");
            //client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
           // client1.setRecordDate(dateString + "_" + "Failure");
            client1.setStatus(imgUrlUnder);//Since the final status will always be UnderProcessing
            listOfTotalClientsUnderProcessing.add(client1);
        });

        mjReportList.stream().forEach(e -> {
            Client client1 = new Client();
            client1.setActivity(e.getId().getType() + "~" + e.getActivity());
            client1.setClient(e.getClientName());
            String dateString = FormatDate.dateToCustomformatString(e.getId().getDateTimeStamp(), "dd-MM-yyyy HH:mm:ss");
           // client1.setRecordDate(dateString.substring(0, dateString.indexOf('.')) + "_" + e.getFinalStatus());
            client1.setRecordDate(dateString + "_" + e.getFinalStatus());
            client1.setStatus(imgUrlUnder);//Since the final status will always be UnderProcessing
            listOfTotalClientsUnderProcessing.add(client1);
        });

        return listOfTotalClientsUnderProcessing;

    }
}

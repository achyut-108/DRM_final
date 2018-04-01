package com.mJunction.drm.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mJunction.drm.common.FormatDate;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by siddhartha.kumar on 4/7/2017.
 */
public class TestingServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestingServiceImpl.class);

    public static void main(String[] args) throws ParseException{

        String startDate = FormatDate.formatStringToString("04/07/2016","MM/dd/yyyy","dd-MM-yyyy");
        LocalDateTime localStartDateTime = FormatDate.StringToDate(startDate,"dd-MM-yyyy")
                                                .toInstant()
                                                .atZone(ZoneId.systemDefault())
                                                .toLocalDateTime();

        LocalDateTime localEndDateTime = localStartDateTime.plus(1, ChronoUnit.DAYS);


        LOGGER.info("[main] : startDate after converting to loacalDateTime : " + localStartDateTime.toString());

        LOGGER.info("[main] : result of comparing two localDates when the enc date is greater : " + localEndDateTime.compareTo(localStartDateTime));

        localStartDateTime = localStartDateTime.plus(1,ChronoUnit.DAYS);

        LOGGER.info("[main] : result of comparing the localDates when start date is equal to endDate : " + localEndDateTime.compareTo(localStartDateTime));

        LOGGER.info("************** isEqual Method : " + localEndDateTime.isEqual(localStartDateTime));


    }
}

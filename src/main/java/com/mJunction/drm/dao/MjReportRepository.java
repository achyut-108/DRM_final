package com.mJunction.drm.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mJunction.drm.common.entity.MjReport;
import com.mJunction.drm.common.entity.MjReportId;
import com.mJunction.drm.common.entity.ProcessStateTable;

import java.util.Date;
import java.util.List;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */
public interface MjReportRepository extends CrudRepository<MjReport,MjReportId>{

    @Query("select count(m.id.dateTimeStamp) from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp between :startDate and :endDate")
    Integer getCountByDateTimeStampAndFinalStatus(Date startDate, Date endDate, String finalStatus);

    @Query("from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp between :startDate and :endDate")
    List<MjReport> findAllByDateTimeStampAndFinalStatus(Date startDate, Date endDate, String finalStatus);

    @Query("from MjReport m where m.id.dateTimeStamp between :startDate and :endDate")
    List<MjReport> findByDateTimeStampBetween(Date startDate,Date endDate);

    @Query("select count(m.id.dateTimeStamp) from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp=:date and m.clientName=:clientName")
    Integer getCountByDateTimeStampAndFinalStatusAndClientName(Date date,String finalStatus,String clientName);

    @Query("select count(m.id.dateTimeStamp) from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp between :startDate and :endDate and m.clientName=:clientName")
    Integer getCountByDateTimeStampAndFinalStatusAndClientName(Date startDate,Date endDate,String finalStatus,String clientName);

    @Query("from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp " +
            "between :startDate and :endDate and m.clientName=:clientName")
    List<MjReport> findAllByDateTimeStampAndFinalStatusAndClientName(Date startDate, Date endDate, String finalStatus,String clientName);

    @Query("from MjReport m where m.id.dateTimeStamp between :startDate and :endDate and m.clientName=:clientName")
    List<MjReport> findAllByDateTimestampAndClientName(Date startDate,Date endDate,String clientName);

    @Query("select count(m.id.dateTimeStamp) from MjReport m where m.finalStatus=:finalStatus and m.id.dateTimeStamp=:date")
    Integer getCountByDateTimeStampAndFinalStatus(Date date,String finalStatus);
}

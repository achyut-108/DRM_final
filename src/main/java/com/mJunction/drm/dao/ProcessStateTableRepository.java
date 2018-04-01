package com.mJunction.drm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mJunction.drm.common.entity.ProcessStateTable;
import com.mJunction.drm.common.entity.ProcessStateTableId;

import java.util.Date;
import java.util.List;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */
public interface ProcessStateTableRepository extends CrudRepository<ProcessStateTable,ProcessStateTableId> {

    @Query("select count(p.id.timeStamp) from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp " +
            "between :startDate and :endDate")
    Integer getCountByTimeStampAndFinalStatus(Date startDate,Date endDate,String finalStatus);


    @Query("select count(p.id.timeStamp) from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp " +
            "between :startDate and :endDate")
    Integer getCountByTimeStampAndFinalStatusDateTest(Date startDate,Date endDate,String finalStatus);

    @Query("from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp " +
            "between :startDate and :endDate")
    public List<ProcessStateTable> findAllByTimeStampAndFinalStatus(Date startDate, Date endDate, String finalStatus);

    @Query("from ProcessStateTable p where p.id.timeStamp " +
            "between :startDate and :endDate")
    public List<ProcessStateTable> findByTimeStampBetween(Date startDate,Date endDate);

    @Query("select count(p.id.timeStamp) from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp =:date " +
            "and p.clientName=:clientName")
    public Integer getCountByTimeStampAndFinalStatusAndClientName(Date date,String finalStatus,String clientName);

    @Query("select count(p.id.timeStamp) from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp between" +
            " :startDate and :endDate and p.clientName=:clientName")
    Integer getCountByTimeStampAndFinalStatusAndClientName(Date startDate,Date endDate,String finalStatus,String clientName);

    @Query("select distinct p.clientName from ProcessStateTable p")
    List<String> getListOfClients();

    @Query("from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp " +
            "between :startDate and :endDate and p.clientName=:clientName")
    public List<ProcessStateTable> findAllByTimeStampAndFinalStatusAndClientName(Date startDate, Date endDate, String finalStatus,String clientName);

    @Query("from ProcessStateTable p where p.id.timeStamp between :startDate and :endDate and p.clientName=:clientName")
    List<ProcessStateTable> findAllByTimestampAndClientName(@Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("clientName") String clientName);

    @Query("select count(p.id.timeStamp) from ProcessStateTable p where p.finalStatus=:finalStatus and p.id.timeStamp =:date")
    Integer getCountByTimeStampAndFinalStatus(Date date,String finalStatus);

}

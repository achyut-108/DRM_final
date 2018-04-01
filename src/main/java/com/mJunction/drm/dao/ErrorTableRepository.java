package com.mJunction.drm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mJunction.drm.common.entity.ErrorTable;
import com.mJunction.drm.common.entity.ErrorTableId;

import java.util.Date;
import java.util.List;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */
public interface ErrorTableRepository extends CrudRepository<ErrorTable,ErrorTableId>{

    @Query("select count(e.id.date) from ErrorTable e where e.id.date between :startDate and :endDate")
    Integer getCountByDateColumn(Date startDate,Date endDate);

    @Query("select count(e.id.date) from ErrorTable e where e.id.date between :startDate and :endDate")
    Integer getCountByDateColumnDateTest(Date startDate, Date endDate);

    @Query("from ErrorTable e where e.id.date between :startDate and :endDate order by e.id.date desc,e.id.type")
    List<ErrorTable> findAllByDateAndFinalStatus(Date startDate, Date endDate);

    @Query("from ErrorTable e where e.id.type=:type")
    List<ErrorTable> findAllByType(String type);

    @Query("from ErrorTable e where e.id.date between :startDate and :endDate order by e.id.date desc,e.id.type")
    List<ErrorTable> findByDateBetweenTwoDates(Date startDate,Date endDate);

    @Query("select count(e.id.date) from ErrorTable e where e.id.date=:date and e.client_name=:clientName")
    Integer getCountByDateAndClientName(Date date,String clientName);

    @Query("select count(e.id.date) from ErrorTable e where e.id.date between :startDate and :endDate and e.client_name=:clientName")
    Integer getCountByDateAndClientName(Date startDate,Date endDate,String clientName);

    @Query("from ErrorTable e where e.id.date between :startDate and :endDate and e.client_name=:clientName order by e.id.date desc,e.id.type")
    List<ErrorTable> findAllByDateAndClientName(Date startDate, Date endDate,String clientName);

    @Query("select count(e.id.date) from ErrorTable e where e.id.date=:date")
    Integer getCountByDate(Date date);
}

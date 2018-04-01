package com.mJunction.drm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mJunction.drm.common.entity.BuisnessErrorTable;
import com.mJunction.drm.common.entity.BuisnessErrorTableId;

import java.util.List;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */
public interface BuisnessErrorTableRepository extends CrudRepository<BuisnessErrorTable,BuisnessErrorTableId> {

    @Query("from BuisnessErrorTable b where b.id.type=:type")
    List<BuisnessErrorTable> findAllByType(String type);
}


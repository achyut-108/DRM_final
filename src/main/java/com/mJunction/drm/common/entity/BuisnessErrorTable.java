package com.mJunction.drm.common.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */

@Entity
@Table(name = "business_error_table")
public class BuisnessErrorTable {

    @EmbeddedId
    private BuisnessErrorTableId id;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "Error_Description")
    private String errorDescription;

    @NotNull
    @Size(min = 1,max = 5000)
    @Column(name = "Stage_State")
    private String stageState;

    @NotNull
    @Column(name = "items_xml",columnDefinition = "blob")
    private Byte[] itemsXml;

    @Size(max = 100)
    @Column(name = "cilent_name")
    private String client_name;

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getStageState() {
        return stageState;
    }

    public void setStageState(String stageState) {
        this.stageState = stageState;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    @Override
    public String toString() {
        return "BuisnessErrorTable{" + "id=" + id + ", errorDescription='" + errorDescription + '\'' + ", stageState='"
                + stageState + '\'' + ", itemsXml=" + Arrays.toString(itemsXml) + ", client_name='"
                + client_name + '\'' + '}';
    }
}

package com.mJunction.drm.common.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */

@Entity
@Table(name = "error_table")
public class ErrorTable{

    @EmbeddedId
    private ErrorTableId id;

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "Error_Description")
    private String errorDescription;


    @NotNull
    @Size(min = 1,max = 5000)
    @Column(name = "Stage_State")
    private String stageState;

    @Size(max = 100)
    @Column(name = "client_name")
    private String client_name;


    public ErrorTableId getId() {
        return id;
    }

    public void setId(ErrorTableId id) {
        this.id = id;
    }

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
        return "ErrorTable{" + "id=" + id + ", errorDescription='" + errorDescription + '\'' + ", stageState='" + stageState + '\'' + ", client_name='" + client_name + '\'' + '}';
    }
}

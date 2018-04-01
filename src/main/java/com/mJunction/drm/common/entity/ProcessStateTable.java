package com.mJunction.drm.common.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */

@Entity
@Table(name = "process_state_table")
public class ProcessStateTable {

    @EmbeddedId
    private ProcessStateTableId id;

    @NotNull
    @Column(name = "version")
    private String version;

    @NotNull
    @Column(name = "xml",columnDefinition = "blob")
    private Byte[] xml;

    @NotNull
    @Column(name = "Initial_status")
    private String intitialStatus;

    @NotNull
    @Column(name = "Final_status")
    private String finalStatus;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "activity")
    private String activity;

    @Column(name = "mdmlobresponse",columnDefinition = "blob")
    private Byte[] mdmLobResponse;

    public ProcessStateTableId getId() {
        return id;
    }

    public void setId(ProcessStateTableId id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Byte[] getXml() {
        return xml;
    }

    public void setXml(Byte[] xml) {
        this.xml = xml;
    }

    public String getIntitialStatus() {
        return intitialStatus;
    }

    public void setIntitialStatus(String intitialStatus) {
        this.intitialStatus = intitialStatus;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Byte[] getMdmLobResponse() {
        return mdmLobResponse;
    }

    public void setMdmLobResponse(Byte[] mdmLobResponse) {
        this.mdmLobResponse = mdmLobResponse;
    }

    @Override
    public String toString() {
        return "ProcessStateTable{" + "id=" + id + ", version='" + version + '\'' + ", xml=" + Arrays.toString(xml)
                + ", intitialStatus='" + intitialStatus + '\'' + ", finalStatus='" + finalStatus + '\''
                + ", clientName='" + clientName + '\'' + ", activity='" + activity + '\'' + ", mdmLobResponse="
                + Arrays.toString(mdmLobResponse) + '}';
    }
}

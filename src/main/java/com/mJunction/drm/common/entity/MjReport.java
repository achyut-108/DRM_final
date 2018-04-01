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
@Table(name = "mj_report")
public class MjReport {

    @EmbeddedId
    private MjReportId id;

    @Column(name = "version",columnDefinition = "text")
    private String version;
    @Column(name = "XML",columnDefinition = "blob")
    private Byte[] xml;
    @Column(name = "Initial_Status",columnDefinition = "text")
    private String intitialStatus;
    @Column(name = "client_Name",columnDefinition = "text")
    private String clientName;
    @Column(name = "activity",columnDefinition = "text")
    private String activity;
    @Column(name = "Final_Status",columnDefinition = "text")
    private String finalStatus;

    public MjReportId getId() {
        return id;
    }

    public void setId(MjReportId id) {
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

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    @Override
    public String toString() {
        return "MjReport{" + "id=" + id + ", version='" + version + '\'' + ", xml=" + Arrays.toString(xml)
                + ", intitialStatus='" + intitialStatus + '\'' + ", clientName='" + clientName + '\'' + ", activity='"
                + activity + '\'' + ", finalStatus='" + finalStatus + '\'' + '}';
    }
}

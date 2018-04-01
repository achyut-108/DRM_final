package com.mJunction.drm.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */

@Embeddable
public class MjReportId implements Serializable{

    @NotNull
    @Column(name = "Type")
    private String type;

    @NotNull
    @Column(name = "datetimestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeStamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(Date dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    @Override
    public String toString() {
        return "MjReportId{" + "type='" + type + '\'' + ", dateTimeStamp=" + dateTimeStamp + '}';
    }
}

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
public class ProcessStateTableId implements Serializable{

    @NotNull
    @Column(name = "Type")
    private String type;

    @NotNull
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ProcessStateTableId{" + "type='" + type + '\'' + ", timeStamp=" + timeStamp + '}';
    }
}

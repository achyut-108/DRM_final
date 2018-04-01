package com.mJunction.drm.common.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siddhartha.kumar on 4/6/2017.
 */

@Embeddable
public class ErrorTableId implements Serializable{

    @NotNull
    @Size(min = 1,max = 255)
    @Column(name = "Type")
    private String type;

    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ErrorTableId{" + "type='" + type + '\'' + ", date=" + date + '}';
    }
}

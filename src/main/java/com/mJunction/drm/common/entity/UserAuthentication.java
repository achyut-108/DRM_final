package com.mJunction.drm.common.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by siddhartha.kumar on 4/4/2017.
 */


@Entity
@Table(name = "userauth")
public class UserAuthentication {

    @Id
    @Column(name = "slno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialNo;
    @Column(name = "userid")
    private String userId;
    @Column(name = "authtoken")
    private String authToken;
    @Column(name = "active")
    private String active;
    @Column(name = "logintime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logInTime;
    @Column(name = "username")
    private String userName;

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(Date logInTime) {
        this.logInTime = logInTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserAuthentication{" + "serialNo=" + serialNo + ", userId='" + userId + '\'' + ", authToken='" + authToken + '\'' + ", active='" + active + '\'' + ", logInTime=" + logInTime + ", userName='" + userName + '\'' + '}';
    }
}

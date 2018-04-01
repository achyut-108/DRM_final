package com.mJunction.drm.pojo;

import com.mJunction.drm.common.validation.ApiValidationBase;
import com.mJunction.drm.common.validation.ErrorCodes;
import com.sun.jersey.api.client.ClientResponse;

import java.util.Objects;

/**
 * Created by siddhartha.kumar on 3/31/2017.
 */
public class LoginDetails extends ApiValidationBase{

    private String userName;
    private String password;
    private String userRole;

    /**
     * @apiNote : fields to be used for output from Login
     */

    private String loginResponse;
    private int loginStatusCode;
    private ClientResponse clientResponse;
    private String loginResponseFromServer;
    private String encryptedToken;
    private String decryptedToken;
    private String checkWebserviceOrDB;

    public LoginDetails(){
    }

    public LoginDetails(String userName, String password, String userRole) {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public int getLoginStatusCode() {
        return loginStatusCode;
    }

    public void setLoginStatusCode(int loginStatusCode) {
        this.loginStatusCode = loginStatusCode;
        if(200 == loginStatusCode || loginResponseFromServer.contains("SUCCESS_MJ_AUTH_9000")) this.loginResponse = "success";
        else{
            if (this.getLoginResponseFromServer().contains("ERROR_MJ_AUTH_9001")) loginResponse = "Not Authenticated";
            else this.loginResponse = "fail";
        }
    }

    public String getLoginResponseFromServer() {
        return loginResponseFromServer;
    }

    public void setLoginResponseFromServer(String loginResponseFromServer) {
        this.loginResponseFromServer = loginResponseFromServer;
    }

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(ClientResponse clientResponse) {
        this.clientResponse = clientResponse;
        this.setLoginResponseFromServer(this.clientResponse.getEntity(String.class));
        this.setLoginStatusCode(this.clientResponse.getStatus());
    }

    public String getLoginResponse() {
        return loginResponse;
    }

    public String getEncryptedToken() {
        return encryptedToken;
    }

    public void setEncryptedToken(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }

    public String getDecryptedToken() {
        return decryptedToken;
    }

    public void setDecryptedToken(String decryptedToken) {
        this.decryptedToken = decryptedToken;
    }

    public String getCheckWebserviceOrDB() {
        return checkWebserviceOrDB;
    }

    public void setCheckWebserviceOrDB(String checkWebserviceOrDB) {
        this.checkWebserviceOrDB = checkWebserviceOrDB;
    }

    public void setLoginResponse(String loginResponse) {
        this.loginResponse = loginResponse;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserName(String userName) {
        if(Objects.isNull(userName) || userName.isEmpty()){
            this.addValidationError(ErrorCodes.INVALID_USER_ID.getCode(),ErrorCodes.INVALID_USER_ID.getDescription(),
                    "userName",userName);
            return;
        }

        this.userName = userName.trim();
    }

    public void setPassword(String password) {
        if(Objects.isNull(password) || password.isEmpty()){
            this.addValidationError(ErrorCodes.INVALID_PASSWORD.getCode(),ErrorCodes.INVALID_PASSWORD.getDescription(),
                    "password",password);
            return;
        }

        this.password = password.trim();
    }

    public void setUserRole(String userRole) {
        if(Objects.nonNull(userRole))
            this.userRole = userRole.trim();
    }

    @Override
    public String toString() {
        return "LoginDetails{" + "userName='" + userName + '\'' + ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' + ", loginResponse='" + loginResponse + '\'' +
                ", loginStatusCode=" + loginStatusCode + ", clientResponse=" + clientResponse +
                ", loginResponseFromServer='" + loginResponseFromServer + '\'' + ", encryptedToken='" +
                encryptedToken + '\'' + ", decryptedToken='" + decryptedToken + '\'' + ", checkWebserviceOrDB='" +
                checkWebserviceOrDB + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginDetails that = (LoginDetails) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return userRole != null ? userRole.equals(that.userRole) : that.userRole == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        return result;
    }
}

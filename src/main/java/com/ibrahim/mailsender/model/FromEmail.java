package com.ibrahim.mailsender.model;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class FromEmail {

    private int id;
    private String email;
    private String password;
    private String recoveryEmail;
    private int totalEmailSent;
    private int lockStatus;
    private int status;
    private String loginReport;
    private boolean successfull;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecoveryEmail() {
        return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }

    public int getTotalEmailSent() {
        return totalEmailSent;
    }

    public void setTotalEmailSent(int totalEmailSent) {
        this.totalEmailSent = totalEmailSent;
    }

    public int getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLoginReport() {
        return loginReport;
    }

    public void setLoginReport(String loginReport) {
        this.loginReport = loginReport;
    }

    public boolean isSuccessfull() {
        return successfull;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }

}

package com.lurodev.apisendemail.Model;

public class MailResponse {
    private boolean success;
    private String reponse;
    private int status;

    public MailResponse() {
    }
    public MailResponse(boolean success, String reponse, int status) {
        this.success = success;
        this.reponse = reponse;
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

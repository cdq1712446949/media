package com.cdq.media.model;

public class PersonInfo {
    private Integer personInfoId;

    private String userId;

    private Byte secretStatus;

    public Integer getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(Integer personInfoId) {
        this.personInfoId = personInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getSecretStatus() {
        return secretStatus;
    }

    public void setSecretStatus(Byte secretStatus) {
        this.secretStatus = secretStatus;
    }
}
package com.eos.parcelnotice.data;


public class ParcelData {
    private String recipient;
    private String createdAt;
    private String updatedAt;
    private int id;
    private String sender;
    private String status;
    private String taker;

    public ParcelData(String recipient, String createdAt, String updatedAt, int id, String sender, String status, String taker) {
        this.recipient = recipient;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.sender = sender;
        this.status = status;
        this.taker = taker;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTaker() {
        return taker;
    }

    public void setTaker(String taker) {
        this.taker = taker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

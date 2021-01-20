package com.eos.parcelnotice.data;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ParcelData {
    private int id;
    private String sender;
    private String status;
    private Date createdAt;
    private int recipient;
    private int dormitory;
    private int room;



    public String getRecipient() {
        return "수신인: "+recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }
    public String getCreatedAt() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return "도착일: "+ format.format(createdAt);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return "보낸사람: "+sender;
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

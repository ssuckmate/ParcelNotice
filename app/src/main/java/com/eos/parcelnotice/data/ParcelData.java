package com.eos.parcelnotice.data;


public class ParcelData {
/*    private String createdAt;
    private String updatedAt;
    private String taker;*/
    private int id;
    private String sender;
    private String status;
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
        return "1/20(수) 도착 ";
    }

/*    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }*/

/*    public String getTaker() {
        return "taker";
    }*/

/*    public void setTaker(String taker) {
       // this.taker = taker;
    }*/

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

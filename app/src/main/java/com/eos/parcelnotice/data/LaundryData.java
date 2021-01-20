package com.eos.parcelnotice.data;


import java.util.Date;

public class LaundryData {
    private int id;
    private String status;
    private int occupant;
    private Date endTime;
    private int dormitory;

    public int getOccupant() {
        return occupant;
    }

    public void setOccupant(int occupant){
        this.occupant = occupant;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

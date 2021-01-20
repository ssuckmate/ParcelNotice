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

    public synchronized String getStatus() {
        return status;
    }

    public synchronized void setStatus(String status) {
        this.status = status;
        if(status=="비었음"){
            setEndTime(null);
            setOccupant(-1);
        }
    }
}

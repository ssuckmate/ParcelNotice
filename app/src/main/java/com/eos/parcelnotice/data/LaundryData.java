package com.eos.parcelnotice.data;


public class LaundryData {
    private String status;
    private int id;
    private int occupant;
    private int dormitory;
    private boolean isUsed;
    private String userName;
    private String machine;
    private volatile int time;

    public LaundryData( String machine,  String userName, String status) {
        this.machine=machine;
        this.userName =userName;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    public void changeIsUsed(){
        isUsed = !isUsed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getTime() {
        return time;
    }

    public synchronized void setTime(int time) {
        this.time = time;
    }
}

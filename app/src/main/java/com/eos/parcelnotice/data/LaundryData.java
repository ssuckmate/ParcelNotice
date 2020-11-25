package com.eos.parcelnotice.data;


public class LaundryData {
    private boolean isUsed;
    private String userName;
    private String machine;
    private String time;

    public LaundryData( String machine, boolean isUsed, String userName, String time) {
        this.isUsed = isUsed;
        this.userName = userName;
        this.machine = machine;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

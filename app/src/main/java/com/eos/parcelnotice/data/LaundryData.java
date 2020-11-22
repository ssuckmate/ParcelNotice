package com.eos.parcelnotice.data;


public class LaundryData {
    private boolean isUsed;
    private String userName;
    private String machine;
    private int time;

    public LaundryData( String machine) {
        this.isUsed = false;
        this.userName = null;
        this.machine = machine;
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

    public void setTime(int time) {
        this.time = time;
    }
}

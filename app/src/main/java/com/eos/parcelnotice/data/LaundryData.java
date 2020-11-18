package com.eos.parcelnotice.data;

public class LaundryData {
    private boolean isUsed;
    private String userId;
    private String machine;

    public LaundryData( String machine) {
        this.isUsed = false;
        this.userId = null;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }
}

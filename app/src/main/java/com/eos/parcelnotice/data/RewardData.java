package com.eos.parcelnotice.data;

import java.util.Date;

public class RewardData {
    private Date arrival;
    private int point;
    private String reason;

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RewardData(Date arrival, int point, String reason) {
        this.arrival = arrival;
        this.point = point;
        this.reason = reason;
    }
}
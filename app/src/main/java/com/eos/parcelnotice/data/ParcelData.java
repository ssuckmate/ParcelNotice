package com.eos.parcelnotice.data;

import java.util.Date;

public class ParcelData {
    private Date arrival;

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String recipient;
    private String status;

    public ParcelData(Date arrival, String recipient, String status) {
        this.arrival = arrival;
        this.recipient = recipient;
        this.status = status;
    }
}

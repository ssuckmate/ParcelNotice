package com.eos.parcelnotice.data;

import java.util.Date;

public class PointData {
    int id;
    String type;
    int amount;
    String reason;
    int user;
    Date createdAt;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public int getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}

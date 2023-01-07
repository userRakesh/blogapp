package com.myblog1.blogapp1.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String datails;

    public ErrorDetails(Date timeStamp, String message, String datails) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.datails = datails;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDatails() {
        return datails;
    }
}

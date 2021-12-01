package com.atvory.currenttime.time;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeResponse {

    @JsonProperty("utc_datetime")
    private String utcDateTime;

    public TimeResponse() {}
    public TimeResponse(String utcDateTime) {
        this.utcDateTime = utcDateTime;
    }
    public String getUtcDateTime() {
        return utcDateTime;
    }
    @Override
    public String toString() {
        return "TimeResponse [utcDateTime=" + utcDateTime + "]";
    }
    
}

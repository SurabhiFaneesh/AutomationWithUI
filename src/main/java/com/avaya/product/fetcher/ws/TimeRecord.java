package com.avaya.product.fetcher.ws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeRecord {
    private static final Logger log = LogManager.getLogger(TimeRecord.class);

    String handle;

    String engineerName;

    String srNumber;

    String activityId;

    Instant date;

    Double hoursBooked;

    private static final String JK_TIME_REC_HANDLE = "handle";

    private static final String JK_TIME_REC_NAME = "owner_name";

    private static final String JK_TIME_REC_HOURS = "hours_booked";

    private static final String JK_TIME_REC_DATE = "date";

    private static final String JK_SR_DATA = "sr";

    private static final String JK_ACT_DATA = "activity";

    public TimeRecord(Elements columns) {
        this.hoursBooked = Double.valueOf(Double.parseDouble(((Element)columns.get(7)).text()));
        this.activityId = ((Element)columns.get(1)).text();
        this.handle = ((Element)columns.get(3)).text();
        this.engineerName = ((Element)columns.get(2)).text();
        String createDateStr = ((Element)columns.get(6)).text();
        this
                .date = LocalDateTime.parse(createDateStr, DateTimeFormatter.ofPattern("M/d/uuuu h:mm:ss a", Locale.US)).atZone(ZoneId.of("UTC")).toInstant();
        this.srNumber = ((Element)columns.get(0)).child(0).text();
        log.debug("ActivityID: " + this.activityId + " Engineer: " + this.handle + " Hours Booked: " + this.hoursBooked);
    }

    public JSONObject getJSONObj(JSONObject srData, JSONObject actData) {
        JSONObject trJson = new JSONObject();
        try {
            trJson.put("handle", this.handle);
            trJson.put("owner_name", this.engineerName);
            trJson.put("hours_booked", this.hoursBooked);
            trJson.put("date", this.date);
            if (srData != null)
                trJson.put("sr", srData);
            if (actData != null)
                trJson.put("activity", actData);
        } catch (JSONException e) {
            log.error("Error creating JSON data for Time record", (Throwable)e);
        }
        return trJson;
    }

    public String getActivityId() {
        return this.activityId;
    }

    public String getID() {
        return this.handle + "_" + this.activityId + "_" + DateTimeFormatter.ofPattern("yyMMddHHmmss", Locale.US).withZone(ZoneId.of("UTC")).format(this.date);
    }

    public Double getHours() {
        return this.hoursBooked;
    }

    public String toString() {
        try {
            return getJSONObj(null, null).toString(3);
        } catch (JSONException e) {
            log.error("Failed to convert Time record to string", (Throwable)e);
            return "";
        }
    }
}

package com.avaya.product.fetcher.ws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class Conref {
    private static final Logger log = LogManager.getLogger(Conref.class);

    String entryDate;

    String owner;

    boolean durationSet = false;

    Double duratoinWithOwnerinHours;

    boolean isQueue;

    boolean isCas;

    boolean isCustCareQueue;

    private static final String JK_OWNER_HANDLE = "owner";

    private static final String JK_DATE = "date";

    private static final String JK_DURATION = "duration";

    private static final String JK_IS_QUEUE = "queue";

    private static final String JK_IS_CAS = "cas";

    public Conref(JSONObject data) {
        try {
            this.owner = data.getString("owner");
            this.entryDate = data.getString("date");
            this.duratoinWithOwnerinHours = Double.valueOf(data.getDouble("duration"));
            this.isCas = data.getBoolean("cas");
            this.isCustCareQueue = data.getBoolean("queue");
        } catch (JSONException e) {
            log.error("Failed to initialize product based on JSON:" + data.toString(), (Throwable)e);
        }
    }

    public String toString() {
        return "Conref Entry - Date: " + this.entryDate + " | Owner: " + this.owner;
    }

    public JSONObject getJSONObj() {
        JSONObject conrefJson = new JSONObject();
        try {
            conrefJson.put("owner", this.owner);
            conrefJson.put("date", this.entryDate);
            conrefJson.put("queue", this.isQueue);
            conrefJson.put("cas", this.isCas);
            if (this.durationSet)
                conrefJson.put("duration", this.duratoinWithOwnerinHours);
        } catch (JSONException e) {
            log.error("Failed to create JSON for ", (Throwable)e);
        }
        return conrefJson;
    }

    public String getOwnerName() {
        return this.owner;
    }

    public String getEntryDate() {
        return this.entryDate;
    }

    public boolean isQueue() {
        return this.isQueue;
    }

    public boolean isCas() {
        return this.isCas;
    }

    public boolean isCustCareQueue() {
        return this.isCustCareQueue;
    }

    public Double getDuration() {
        return this.duratoinWithOwnerinHours;
    }
}


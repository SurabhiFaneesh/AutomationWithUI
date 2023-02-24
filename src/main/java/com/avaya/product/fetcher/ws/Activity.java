package com.avaya.product.fetcher.ws;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class Activity {
    private static final Logger log = LogManager.getLogger(Activity.class);

    public static final String ACTIVITY_ADM1 = "ADM1 - Problem Statement";

    public static final String ACTIVITY_ADM2 = "ADM2 - Details/Findings";

    public static final String ACTIVITY_ADM3 = "ADM3 - Problem Clarification";

    public static final String ACTIVITY_ADM4 = "ADM4 - Cause";

    public static final String ACTIVITY_ADM5 = "ADM5 - Solution";

    public static final String ACTIVITY_ADM6 = "ADM6 - Knowledge Management";

    public static final List<String> ADMFilter = Arrays
            .asList(new String[] { "ADM1 - Problem Statement", "ADM2 - Details/Findings", "ADM3 - Problem Clarification",
                    "ADM4 - Cause", "ADM5 - Solution", "ADM6 - Knowledge Management" });

    private static final String JK_ACT_ID = "activity_id";

    private static final String JK_ACT_CREATE_DATE = "create_date";

    private static final String JK_ACT_DATE = "date";

    private static final String JK_ACT_TYPE = "type";

    private static final String JK_ACT_STATUS = "status";

    private static final String JK_ACT_OWNER_HANDLE = "owner";

    private static final String JK_ACT_OWNER_NAME = "owner_name";

    private static final String JK_ACT_DESCRIPTION = "description";

    private static final String JK_ACT_AGE = "age";

    private static final String JK_ACT_ENT_FLAG = "entitlement_flag";

    private static final String JK_ACT_EXTRA_CONTRIBUTION = "extra_contribution";

    private static final String JK_OWNED_BY_CAS = "owned_by_cas";

    private static final String JK_HOURS_BOOKED = "hours_booked";

    private static final String JK_ACT_COMMENTS = "comments";

    private static final String JK_SR_DATA = "sr";

    private static final String JK_COLLABORATION_TYPE = "collaboration_type";

    private static final String JK_LAST_REFRESHED = "last_refreshed";

    private static final String JK_ACT_CREATE_FY = "create_fiscal_year";

    private static final String JK_ACT_CREATE_Q = "create_quarter";

    private static final String JK_ACT_CREATE_M = "create_month";

    private String type;

    private String activity_id;

    private Instant date;

    private String owner;

    private String ownerName;

    private String status;

    private String srNumber;

    private String description;

    private String created_date;

    private double hours_booked;

    private String comment;

    private Product extraContribution;

    private String entitlementFlag;

    private boolean ownedByCas;

    private String collaborationType;

    private TimeRecordList timeRecords = new TimeRecordList();

    private Instant lastRefreshed;

    private String createFiscalYear;

    private String createQuarter;

    private String createMonth;

    private String extraContributionType;

    private String extraContributionCategory;

    String origType;

    public Instant getDate() {
        return this.date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrNumber() {
        return this.srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public Product getExtraContribution() {
        return this.extraContribution;
    }

    public void setExtraContribution(Product extraContribution) {
        this.extraContribution = extraContribution;
    }

    public String getEntitlementFlag() {
        return this.entitlementFlag;
    }

    public void setEntitlementFlag(String entitlementFlag) {
        this.entitlementFlag = entitlementFlag;
    }

    public boolean isOwnedByCas() {
        return this.ownedByCas;
    }

    public void setOwnedByCas(boolean ownedByCas) {
        this.ownedByCas = ownedByCas;
    }

    public String getCollaborationType() {
        return this.collaborationType;
    }

    public void setCollaborationType(String collaborationType) {
        this.collaborationType = collaborationType;
    }

    public TimeRecordList getTimeRecords() {
        return this.timeRecords;
    }

    public void setTimeRecords(TimeRecordList timeRecords) {
        this.timeRecords = timeRecords;
    }

    public Instant getLastRefreshed() {
        return this.lastRefreshed;
    }

    public void setLastRefreshed(Instant lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getCreateFiscalYear() {
        return this.createFiscalYear;
    }

    public void setCreateFiscalYear(String createFiscalYear) {
        this.createFiscalYear = createFiscalYear;
    }

    public String getCreateQuarter() {
        return this.createQuarter;
    }

    public void setCreateQuarter(String createQuarter) {
        this.createQuarter = createQuarter;
    }

    public String getCreateMonth() {
        return this.createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }

    public String getOrigType() {
        return this.origType;
    }

    public void setOrigType(String origType) {
        this.origType = origType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void prepareActivityRecord(String activityId, JSONObject data) {
        try {
            this.ownerName = data.getString("owner_name");
            this.hours_booked = data.getDouble("hours_booked");
            this.created_date = data.getString("date");
            JSONObject object = data.getJSONObject("activity");
            this.type = object.getString("type");
            this.activity_id = object.getString("activity_id");
            if (this.type != null && !this.type.isEmpty() && this.type.equalsIgnoreCase("Extra")) {
                JSONObject extraContributionObject = object.getJSONObject("extra_contribution");
                this.extraContributionType = extraContributionObject.getString("name");
                this.extraContributionCategory = extraContributionObject.getString("category");
            }
        } catch (JSONException e) {
            log.error("Failed to initialize activity based on JSON:" + data
                    .toString() + " for activityId#" + activityId, (Throwable) e);
        }
    }

    public Activity() {
    }

    public Activity(String srID, JSONObject data) {
        try {
            this.type = data.getString("type");
            this.activity_id = data.getString("activity_id");
            this.description = data.getString("description");
            this.hours_booked = data.getDouble("hours_booked");
            try {
                this.comment = data.getString("comments");
            } catch (JSONException e) {
                System.out.println("No Comment for SR# " + srID);
                this.comment = "";
            }
        } catch (JSONException e) {
            log.error("Failed to initialize product based on JSON:" + data.toString() + " for SR#" + srID, (Throwable) e);
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public double getHours_booked() {
        return this.hours_booked;
    }

    public void setHours_booked(double hours_booked) {
        this.hours_booked = hours_booked;
    }

    public String getComment() {
        if (this.comment == null)
            this.comment = "";
        return this.comment;
    }

    public String toString() {
        return "Activity [type=" + this.type + ", activity_id=" + this.activity_id + ", date=" + this.date + ", owner="
                + this.owner + ", ownerName=" + this.ownerName + ", status=" + this.status + ", srNumber=" + this.srNumber
                + ", description=" + this.description + ", created_date=" + this.created_date + ", hours_booked="
                + this.hours_booked + ", comment=" + this.comment + ", extraContribution=" + this.extraContribution
                + ", entitlementFlag=" + this.entitlementFlag + ", ownedByCas=" + this.ownedByCas + ", collaborationType="
                + this.collaborationType + ", timeRecords=" + this.timeRecords + ", lastRefreshed=" + this.lastRefreshed
                + ", createFiscalYear=" + this.createFiscalYear + ", createQuarter=" + this.createQuarter + ", createMonth="
                + this.createMonth + ", extraContributionType=" + this.extraContributionType + ", extraContributionCategory="
                + this.extraContributionCategory + ", origType=" + this.origType + "]";
    }

    public String getExtraContributionType() {
        return this.extraContributionType;
    }

    public void setExtraContributionType(String extraContributionType) {
        this.extraContributionType = extraContributionType;
    }

    public String getExtraContributionCategory() {
        return this.extraContributionCategory;
    }

    public void setExtraContributionCategory(String extraContributionCategory) {
        this.extraContributionCategory = extraContributionCategory;
    }
}


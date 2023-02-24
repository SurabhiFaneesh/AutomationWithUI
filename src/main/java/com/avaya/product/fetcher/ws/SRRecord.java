package com.avaya.product.fetcher.ws;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SRRecord {
    private String srNumber;

    private Product casProduct;

    private String adm1;

    private String adm2;

    private String adm3;

    private String adm4;

    private String adm5;

    private String adm6;

    private String queue;
    private String close_fiscal_year;
    private float hours_in_cas_queues;
    private String service_action;
    private String restore_date;

    private double hours_booked;

    private String resolve_date;

    private String close_date;

    private String create_date;

    private double cas_hours_booked;
    private String customer_segment;
    private String create_quarter;
    private float rcpt_to_comp;
    private float rcpt_to_comp_9_hr;
    private String cause;
    private String contact_type;
    private String cust_care;
    private String close_quarter;
    private float cust_care_resolution_time_hours;
    private String supervisor;
    private String supervisor_handle;
    private float rcpt_to_rest_9_hr;
    private float rcpt_to_rest;
    private String original_severity;

    private String region;
    private String parent_id;
    private String create_fiscal_year;
    private String country_code;
    private String country;
    private String parent_name;
    private String resolution_detail;
    private String created_by;
    private float rcpt_to_reso;
    private String bp_link_id;
    private String security_restriced;
    private String type;
    private String source;
    private String in_support_of_site;
    private String severity_ordered;

    private float rcpt_to_reso_9_hr;
    private String queue_date;
    private String cas_type;
    private String owned_by_cas;
    private String close_month;
    private String capsule_team;
    private String owner_name;

    private String resolution_note;

    private String customer_name;

    List<Conref> conRefEntries = new ArrayList<Conref>();

    List<Activity> admEntries = new ArrayList<Activity>();

    List<Activity> activityList = new ArrayList<Activity>();

    private static final String JK_OWNER_HISTORY = "owner_history";

    private static final String JK_CASPRODUCT = "casproduct";

    private static final String JK_ADM = "adm";

    public static final String JK_PARENT_NAME = "parent_name";

    public static final String JK_CREATE_DATE = "create_date";

    public static final String JK_CLOSE_DATE = "close_date";

    public static final String JK_RESTORE_DATE = "restore_date";

    private static final String JK_RESOLUTION_NOTE = "resolution_note";

    public static final String JK_RESOLVE_DATE = "resolve_date";

    private static final String JK_HOURS_BOOKED = "hours_booked";

    private static final String JK_CAS_HOURS_BOOKED = "cas_hours_booked";

    public SRRecord(String srNumber) {
        this.srNumber = srNumber;
    }

    public SRRecord(JSONObject data) {
        String sr_num = "";

        try {
            try {
                this.srNumber = data.getString("sr_no");
                sr_num = this.srNumber;
            } catch (JSONException e) {
                this.srNumber = "";
            }
            try {
                this.srNumber = data.getString("sr_no");
                sr_num = this.srNumber;
            } catch (JSONException e) {
                this.srNumber = "";
            }
            try {
                this.casProduct = new Product(data.getJSONObject("casproduct"));
            } catch (JSONException e) {
                this.casProduct = new Product("UNKNOWN", "UNKNOWN", "UNKNOWN");
            }
            try {
                this.restore_date = data.getString("restore_date");
            } catch (JSONException e) {
                this.restore_date = "";
            }
            try {
                this.resolve_date = data.getString("restore_date");
            } catch (JSONException e) {
                this.resolve_date = "";
            }
            try {
                this.restore_date = data.getString("restore_date");
            } catch (JSONException e) {
                this.restore_date = "";
            }
            try {
                this.hours_booked = data.getDouble("hours_booked");
            } catch (JSONException e) {
                this.hours_booked = 0.0D;
            }
            try {
                this.cas_hours_booked = data.getDouble("cas_hours_booked");
            } catch (JSONException e) {
                this.cas_hours_booked = 0.0D;
            }
            try {
                this.rcpt_to_comp = data.getFloat("rcpt_to_comp");
            } catch (JSONException e) {
                this.rcpt_to_comp = 0.0f;
            }
            try {
                this.rcpt_to_comp_9_hr = data.getFloat("rcpt_to_comp_9_hr");
            } catch (JSONException e) {
                this.rcpt_to_comp_9_hr = 0.0f;
            }
            try {
                this.cust_care_resolution_time_hours = data.getFloat("cust_care_resolution_time_hours");
            } catch (JSONException e) {
                this.cust_care_resolution_time_hours = 0.0f;
            }
            try {
                this.hours_in_cas_queues = data.getFloat("hours_in_cas_queues");
            } catch (JSONException e) {
                this.hours_in_cas_queues = 0.0f;
            }
            try {
                this.rcpt_to_reso = data.getFloat("rcpt_to_reso");
            } catch (JSONException e) {
                this.rcpt_to_reso = 0.0f;
            }
            try {
                this.rcpt_to_reso_9_hr = data.getFloat("rcpt_to_reso_9_hr");
            } catch (JSONException e) {
                this.rcpt_to_reso_9_hr = 0.0f;
            }
            try {
                this.close_date = data.getString("close_date");
            } catch (JSONException e) {
                this.close_date = "";
            }
            try {
                this.customer_name = data.getString("parent_name");
            } catch (JSONException e) {
                this.close_date = "";
            }
            try {
                this.resolution_note = data.getString("resolution_note");
            } catch (JSONException e) {
                this.close_date = "";
            }
            try {
                this.cas_hours_booked = data.getDouble("cas_hours_booked");
            } catch (JSONException e) {
                this.cas_hours_booked = 0.0d;
            }
            try {
                this.customer_segment = data.getString("customer_segment");
            } catch (JSONException e) {
                this.customer_segment = "";
            }
            try {
                this.create_quarter = data.getString("create_quarter");
            } catch (JSONException e) {
                this.create_quarter = "";
            }
            try {
                this.cause = data.getString("cause");
            } catch (JSONException e) {
                this.cause = "";
            }
            try {
                this.contact_type = data.getString("contact_type");
            } catch (JSONException e) {
                this.contact_type = "";
            }
            try {
                this.cust_care = data.getString("cust_care");
            } catch (JSONException e) {
                this.cust_care = "";
            }
            try {
                this.close_quarter = data.getString("close_quarter");
            } catch (JSONException e) {
                this.close_quarter = "";
            }
            try {
                this.supervisor = data.getString("supervisor");
            } catch (JSONException e) {
                this.supervisor = "";
            }
            try {
                this.supervisor_handle = data.getString("supervisor_handle");
            } catch (JSONException e) {
                this.supervisor_handle = "";
            }

            try {
                this.original_severity = data.getString("original_severity");
            } catch (JSONException e) {
                this.original_severity = "";
            }

            try {
                this.queue = data.getString("queue");
            } catch (JSONException e) {
                this.queue = "";
            }

            try {
                this.close_fiscal_year = data.getString("close_fiscal_year");
            } catch (JSONException e) {
                this.close_fiscal_year = "";
            }

            try {
                this.service_action = data.getString("service_action");
            } catch (JSONException e) {
                this.service_action = "";
            }
            try {
                this.restore_date = data.getString("restore_date");
            } catch (JSONException e) {
                this.restore_date = "";
            }

            try {
                this.region = data.getString("region");
            } catch (JSONException e) {
                this.region = "";
            }
            try {
                this.parent_id = data.getString("parent_id");
            } catch (JSONException e) {
                this.parent_id = "";
            }
            try {
                this.create_fiscal_year = data.getString("create_fiscal_year");
            } catch (JSONException e) {
                this.country_code = "";
            }
            try {
                this.supervisor_handle = data.getString("supervisor_handle");
            } catch (JSONException e) {
                this.supervisor_handle = "";
            }
            try {
                this.country_code = data.getString("country_code");
            } catch (JSONException e) {
                this.country_code = "";
            }
            try {
                this.country = data.getString("country");
            } catch (JSONException e) {
                this.country = "";
            }
            try {
                this.parent_name = data.getString("parent_name");
            } catch (JSONException e) {
                this.parent_name = "";
            }
            try {
                this.resolution_detail = data.getString("resolution_detail");
            } catch (JSONException e) {
                this.resolution_detail = "";
            }
            try {
                this.created_by = data.getString("created_by");
            } catch (JSONException e) {
                this.created_by = "";
            }
            try {
                this.bp_link_id = data.getString("bp_link_id");
            } catch (JSONException e) {
                this.bp_link_id = "";
            }
            try {
                this.security_restriced = data.getString("security_restriced");
            } catch (JSONException e) {
                this.security_restriced = "";
            }
            try {
                this.type = data.getString("type");
            } catch (JSONException e) {
                this.type = "";
            }
            try {
                this.source = data.getString("source");
            } catch (JSONException e) {
                this.source = "";
            }
            try {
                this.in_support_of_site = data.getString("in_support_of_site");
            } catch (JSONException e) {
                this.in_support_of_site = "";
            }
            try {
                this.severity_ordered = data.getString("severity_ordered");
            } catch (JSONException e) {
                this.severity_ordered = "";
            }
            try {
                this.queue_date = data.getString("queue_date");
            } catch (JSONException e) {
                this.queue_date = "";
            }
            try {
                this.owner_name = data.getString("owner_name");
            } catch (JSONException e) {
                this.owner_name = "";
            }
            try {
                this.cas_type = data.getString("cas_type");
            } catch (JSONException e) {
                this.cas_type = "";
            }
            try {
                this.owned_by_cas = data.getString("owned_by_cas");
            } catch (JSONException e) {
                this.owned_by_cas = "";
            }
            try {
                this.close_month = data.getString("close_month");
            } catch (JSONException e) {
                this.close_month = "";
            }
            try {
                this.capsule_team = data.getString("capsule_team");
            } catch (JSONException e) {
                this.capsule_team = "";
            }
            this.create_date = data.getString("create_date");
            JSONArray conrefs = data.getJSONArray("owner_history");
            Iterator<Object> iterator = conrefs.iterator();
            while (iterator.hasNext()) {
                Conref conref = new Conref((JSONObject) iterator.next());
                this.conRefEntries.add(conref);
            }
            JSONArray admList = data.getJSONArray("adm");
            System.out.println(admList);
            Iterator<Object> admIterator = admList.iterator();
            while (admIterator.hasNext())
                this.admEntries.add(new Activity(this.srNumber, (JSONObject) admIterator.next()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Conref> getConrefs() {
        return this.conRefEntries;
    }

    public List<Activity> getADMEntries() {
        return this.admEntries;
    }

    public String getSrNumber() {
        return this.srNumber;
    }

    public String getRestore_date() {
        return this.restore_date;
    }

    public double getHours_booked() {
        return this.hours_booked;
    }

    public String getResolve_date() {
        return this.resolve_date;
    }

    public String getClose_date() {
        return this.close_date;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public double getCas_hours_booked() {
        return this.cas_hours_booked;
    }

    public Product getCasProduct() {
        return this.casProduct;
    }

    public String getResolution_note() {
        return this.resolution_note;
    }

    public String getCustomer_name() {
        return this.customer_name;
    }

    public List<Activity> getActivityList() {
        return this.activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public String toString() {
        return "SRRecord [srNumber=" + srNumber + ", casProduct=" + casProduct + ", adm1=" + adm1 + ", adm2=" + adm2
                + ", adm3=" + adm3 + ", adm4=" + adm4 + ", adm5=" + adm5 + ", adm6=" + adm6 + ", queue=" + queue
                + ", close_fiscal_year=" + close_fiscal_year + ", hours_in_cas_queues=" + hours_in_cas_queues
                + ", service_action=" + service_action + ", restore_date=" + restore_date + ", hours_booked="
                + hours_booked + ", resolve_date=" + resolve_date + ", close_date=" + close_date + ", create_date="
                + create_date + ", cas_hours_booked=" + cas_hours_booked + ", customer_segment=" + customer_segment
                + ", create_quarter=" + create_quarter + ", rcpt_to_comp=" + rcpt_to_comp + ", rcpt_to_comp_9_hr="
                + rcpt_to_comp_9_hr + ", cause=" + cause + ", contact_type=" + contact_type + ", cust_care=" + cust_care
                + ", close_quarter=" + close_quarter + ", cust_care_resolution_time_hours="
                + cust_care_resolution_time_hours + ", supervisor=" + supervisor + ", supervisor_handle="
                + supervisor_handle + ", rcpt_to_rest_9_hr=" + rcpt_to_rest_9_hr + ", rcpt_to_rest=" + rcpt_to_rest
                + ", original_severity=" + original_severity + ", region=" + region + ", parent_id=" + parent_id
                + ", create_fiscal_year=" + create_fiscal_year + ", country_code=" + country_code + ", country="
                + country + ", parent_name=" + parent_name + ", resolution_detail=" + resolution_detail
                + ", created_by=" + created_by + ", rcpt_to_reso=" + rcpt_to_reso + ", bp_link_id=" + bp_link_id
                + ", security_restriced=" + security_restriced + ", type=" + type + ", source=" + source
                + ", in_support_of_site=" + in_support_of_site + ", severity_ordered=" + severity_ordered
                + ", rcpt_to_reso_9_hr=" + rcpt_to_reso_9_hr + ", queue_date=" + queue_date + ", cas_type=" + cas_type
                + ", owned_by_cas=" + owned_by_cas + ", close_month=" + close_month + ", capsule_team=" + capsule_team
                + ", owner_name=" + owner_name + ", resolution_note=" + resolution_note + ", customer_name="
                + customer_name + ", conRefEntries=" + conRefEntries + ", admEntries=" + admEntries + ", activityList="
                + activityList + "]";
    }

    public String getAdm1() {
        return adm1;
    }

    public void setAdm1(String adm1) {
        this.adm1 = adm1;
    }

    public String getAdm2() {
        return adm2;
    }

    public void setAdm2(String adm2) {
        this.adm2 = adm2;
    }

    public String getAdm3() {
        return adm3;
    }

    public void setAdm3(String adm3) {
        this.adm3 = adm3;
    }

    public String getAdm4() {
        return adm4;
    }

    public void setAdm4(String adm4) {
        this.adm4 = adm4;
    }

    public String getAdm5() {
        return adm5;
    }

    public void setAdm5(String adm5) {
        this.adm5 = adm5;
    }

    public String getAdm6() {
        return adm6;
    }

    public void setAdm6(String adm6) {
        this.adm6 = adm6;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getClose_fiscal_year() {
        return close_fiscal_year;
    }

    public void setClose_fiscal_year(String close_fiscal_year) {
        this.close_fiscal_year = close_fiscal_year;
    }

    public float getHours_in_cas_queues() {
        return hours_in_cas_queues;
    }

    public void setHours_in_cas_queues(float hours_in_cas_queues) {
        this.hours_in_cas_queues = hours_in_cas_queues;
    }

    public String getService_action() {
        return service_action;
    }

    public void setService_action(String service_action) {
        this.service_action = service_action;
    }

    public String getCustomer_segment() {
        return customer_segment;
    }

    public void setCustomer_segment(String customer_segment) {
        this.customer_segment = customer_segment;
    }

    public String getCreate_quarter() {
        return create_quarter;
    }

    public void setCreate_quarter(String create_quarter) {
        this.create_quarter = create_quarter;
    }

    public float getRcpt_to_comp() {
        return rcpt_to_comp;
    }

    public void setRcpt_to_comp(float rcpt_to_comp) {
        this.rcpt_to_comp = rcpt_to_comp;
    }

    public float getRcpt_to_comp_9_hr() {
        return rcpt_to_comp_9_hr;
    }

    public void setRcpt_to_comp_9_hr(float rcpt_to_comp_9_hr) {
        this.rcpt_to_comp_9_hr = rcpt_to_comp_9_hr;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getCust_care() {
        return cust_care;
    }

    public void setCust_care(String cust_care) {
        this.cust_care = cust_care;
    }

    public String getClose_quarter() {
        return close_quarter;
    }

    public void setClose_quarter(String close_quarter) {
        this.close_quarter = close_quarter;
    }

    public float getCust_care_resolution_time_hours() {
        return cust_care_resolution_time_hours;
    }

    public void setCust_care_resolution_time_hours(float cust_care_resolution_time_hours) {
        this.cust_care_resolution_time_hours = cust_care_resolution_time_hours;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor_handle() {
        return supervisor_handle;
    }

    public void setSupervisor_handle(String supervisor_handle) {
        this.supervisor_handle = supervisor_handle;
    }

    public float getRcpt_to_rest_9_hr() {
        return rcpt_to_rest_9_hr;
    }

    public void setRcpt_to_rest_9_hr(float rcpt_to_rest_9_hr) {
        this.rcpt_to_rest_9_hr = rcpt_to_rest_9_hr;
    }

    public float getRcpt_to_rest() {
        return rcpt_to_rest;
    }

    public void setRcpt_to_rest(float rcpt_to_rest) {
        this.rcpt_to_rest = rcpt_to_rest;
    }

    public String getOriginal_severity() {
        return original_severity;
    }

    public void setOriginal_severity(String original_severity) {
        this.original_severity = original_severity;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCreate_fiscal_year() {
        return create_fiscal_year;
    }

    public void setCreate_fiscal_year(String create_fiscal_year) {
        this.create_fiscal_year = create_fiscal_year;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getResolution_detail() {
        return resolution_detail;
    }

    public void setResolution_detail(String resolution_detail) {
        this.resolution_detail = resolution_detail;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public float getRcpt_to_reso() {
        return rcpt_to_reso;
    }

    public void setRcpt_to_reso(float rcpt_to_reso) {
        this.rcpt_to_reso = rcpt_to_reso;
    }

    public String getBp_link_id() {
        return bp_link_id;
    }

    public void setBp_link_id(String bp_link_id) {
        this.bp_link_id = bp_link_id;
    }

    public String getSecurity_restriced() {
        return security_restriced;
    }

    public void setSecurity_restriced(String security_restriced) {
        this.security_restriced = security_restriced;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIn_support_of_site() {
        return in_support_of_site;
    }

    public void setIn_support_of_site(String in_support_of_site) {
        this.in_support_of_site = in_support_of_site;
    }

    public String getSeverity_ordered() {
        return severity_ordered;
    }

    public void setSeverity_ordered(String severity_ordered) {
        this.severity_ordered = severity_ordered;
    }

    public float getRcpt_to_reso_9_hr() {
        return rcpt_to_reso_9_hr;
    }

    public void setRcpt_to_reso_9_hr(float rcpt_to_reso_9_hr) {
        this.rcpt_to_reso_9_hr = rcpt_to_reso_9_hr;
    }

    public String getQueue_date() {
        return queue_date;
    }

    public void setQueue_date(String queue_date) {
        this.queue_date = queue_date;
    }

    public String getCas_type() {
        return cas_type;
    }

    public void setCas_type(String cas_type) {
        this.cas_type = cas_type;
    }

    public String getOwned_by_cas() {
        return owned_by_cas;
    }

    public void setOwned_by_cas(String owned_by_cas) {
        this.owned_by_cas = owned_by_cas;
    }

    public String getClose_month() {
        return close_month;
    }

    public void setClose_month(String close_month) {
        this.close_month = close_month;
    }

    public String getCapsule_team() {
        return capsule_team;
    }

    public void setCapsule_team(String capsule_team) {
        this.capsule_team = capsule_team;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public List<Conref> getConRefEntries() {
        return conRefEntries;
    }

    public void setConRefEntries(List<Conref> conRefEntries) {
        this.conRefEntries = conRefEntries;
    }

    public List<Activity> getAdmEntries() {
        return admEntries;
    }

    public void setAdmEntries(List<Activity> admEntries) {
        this.admEntries = admEntries;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public void setCasProduct(Product casProduct) {
        this.casProduct = casProduct;
    }

    public void setRestore_date(String restore_date) {
        this.restore_date = restore_date;
    }

    public void setHours_booked(double hours_booked) {
        this.hours_booked = hours_booked;
    }

    public void setResolve_date(String resolve_date) {
        this.resolve_date = resolve_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setCas_hours_booked(double cas_hours_booked) {
        this.cas_hours_booked = cas_hours_booked;
    }

    public void setResolution_note(String resolution_note) {
        this.resolution_note = resolution_note;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

}


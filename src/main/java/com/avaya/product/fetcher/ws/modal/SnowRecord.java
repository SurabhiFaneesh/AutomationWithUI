package com.avaya.product.fetcher.ws.modal;

import org.json.JSONException;
import org.json.JSONObject;

import com.avaya.product.fetcher.ws.Product;

import java.time.Duration;
import java.time.Instant;

public class SnowRecord {

    private String assignment_group;
    private String cas_subtype;
    private String cas_type;
    private Product casproduct;
    private String ci_name;
    private String service_model;
    private String close_date;
    private String close_fiscal_year;
    private String close_month;
    private String close_quarter;
    private String create_date;
    private String create_fiscal_year;
    private String create_month;
    private String create_quarter;
    private String created_by;
    private String customer;
    private String description;
    private String fl;
    private String impact;
    private String last_updated;
    private String last_updated_by;
    private String location_address;
    private String location_name;
    private String original_priority;
    private String owned_by_cas;
    private String owner_hanle;
    private String owner_name;
    private String parent_name;
    private String primary_contact_email;
    private String priority;
    private String product;
    private String reassignment_count;
    private String resolve_date;
    private String response_date;
    private String restore_date;
    private String short_description;
    private String status;
    private String status_reason;
    private String supervisor;
    private String supervisor_handle;
    private String ticket_no;
    private String type;
    private String _class;
    private String subclass;
    private float responseTime;
    private float resolutionTime;
    private float restorationTime;
    private float closureTime;


    public float getResponseTime() {
        if(response_date!=null && !response_date.isEmpty()) {
            Duration time =  Duration.between(Instant.parse( create_date ), Instant.parse( response_date ));
            responseTime= time.toMinutes()/60;
        }else {
            responseTime=0f;
        }
        return responseTime;
    }

    public float getResolutionTime() {
        if(resolve_date!=null && !resolve_date.isEmpty()) {
            Duration time =  Duration.between(Instant.parse( create_date ), Instant.parse( resolve_date ));
            resolutionTime= time.toMinutes()/60;
        }
        return resolutionTime;
    }

    public float getRestorationTime() {
        if(restore_date!=null && !restore_date.isEmpty()) {
            Duration time =  Duration.between(Instant.parse( create_date ), Instant.parse( restore_date ));
            restorationTime= time.toMinutes()/60;
        }
        return restorationTime;
    }

    public float getClosureTime() {
        if(close_date!=null && !close_date.isEmpty()) {
            Duration time =  Duration.between(Instant.parse( create_date ), Instant.parse( close_date ));
            closureTime= time.toMinutes()/60;
        }
        return closureTime;
    }

    public String getAssignment_group() {
        return assignment_group;
    }

    public void setAssignment_group(String assignment_group) {
        this.assignment_group = assignment_group;
    }

    public String getCas_subtype() {
        return cas_subtype;
    }

    public void setCas_subtype(String cas_subtype) {
        this.cas_subtype = cas_subtype;
    }

    public String getCas_type() {
        return cas_type;
    }

    public void setCas_type(String cas_type) {
        this.cas_type = cas_type;
    }

    public Product getCasproduct() {
        return casproduct;
    }

    public void setCasproduct(Product casproduct) {
        this.casproduct = casproduct;
    }

    public String getCi_name() {
        return ci_name;
    }

    public void setCi_name(String ci_name) {
        this.ci_name = ci_name;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public String getClose_fiscal_year() {
        return close_fiscal_year;
    }

    public void setClose_fiscal_year(String close_fiscal_year) {
        this.close_fiscal_year = close_fiscal_year;
    }

    public String getClose_month() {
        return close_month;
    }

    public void setClose_month(String close_month) {
        this.close_month = close_month;
    }

    public String getClose_quarter() {
        return close_quarter;
    }

    public void setClose_quarter(String close_quarter) {
        this.close_quarter = close_quarter;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_fiscal_year() {
        return create_fiscal_year;
    }

    public void setCreate_fiscal_year(String create_fiscal_year) {
        this.create_fiscal_year = create_fiscal_year;
    }

    public String getCreate_month() {
        return create_month;
    }

    public void setCreate_month(String create_month) {
        this.create_month = create_month;
    }

    public String getCreate_quarter() {
        return create_quarter;
    }

    public void setCreate_quarter(String create_quarter) {
        this.create_quarter = create_quarter;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getOriginal_priority() {
        return original_priority;
    }

    public void setOriginal_priority(String original_priority) {
        this.original_priority = original_priority;
    }

    public String getOwned_by_cas() {
        return owned_by_cas;
    }

    public void setOwned_by_cas(String owned_by_cas) {
        this.owned_by_cas = owned_by_cas;
    }

    public String getOwner_hanle() {
        return owner_hanle;
    }

    public void setOwner_hanle(String owner_hanle) {
        this.owner_hanle = owner_hanle;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getPrimary_contact_email() {
        return primary_contact_email;
    }

    public void setPrimary_contact_email(String primary_contact_email) {
        this.primary_contact_email = primary_contact_email;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getReassignment_count() {
        return reassignment_count;
    }

    public void setReassignment_count(String reassignment_count) {
        this.reassignment_count = reassignment_count;
    }

    public String getResolve_date() {
        return resolve_date;
    }

    public void setResolve_date(String resolve_date) {
        this.resolve_date = resolve_date;
    }

    public String getResponse_date() {
        return response_date;
    }

    public void setResponse_date(String response_date) {
        this.response_date = response_date;
    }

    public String getRestore_date() {
        return restore_date;
    }

    public void setRestore_date(String restore_date) {
        this.restore_date = restore_date;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_reason() {
        return status_reason;
    }

    public void setStatus_reason(String status_reason) {
        this.status_reason = status_reason;
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

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getService_model() {
        return service_model;
    }

    public void setService_model(String service_model) {
        this.service_model = service_model;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public SnowRecord(JSONObject data) {
        try {
            this.ticket_no = data.getString("ticket_no");
        } catch (JSONException e) {
            this.ticket_no = "";
        }
        try {
            this.assignment_group = data.getString("assignment_group");
        } catch (JSONException e) {
            this.assignment_group = "";
        }
        try {
            this.cas_subtype = data.getString("cas_subtype");
        } catch (JSONException e) {
            this.cas_subtype = "";
        }
        try {
            this.cas_type = data.getString("cas_type");
        } catch (JSONException e) {
            this.cas_type = "";
        }
        try {
            this.casproduct = new Product(data.getJSONObject("casproduct"));
        } catch (JSONException e) {
            this.casproduct = new Product("UNKNOWN", "UNKNOWN", "UNKNOWN");
        }
        try {
            this.ci_name = data.getString("ci_name");
        } catch (JSONException e) {
            this.ci_name = "";
        }
        try {
            this.close_date = data.getString("close_date");
        } catch (JSONException e) {
            this.close_date = "";
        }
        try {
            this.close_fiscal_year = data.getString("close_fiscal_year");
        } catch (JSONException e) {
            this.close_fiscal_year = "";
        }

        try {
            this.close_month = data.getString("close_month");
        } catch (JSONException e) {
            this.close_month = "";
        }

        try {
            this.close_quarter = data.getString("close_quarter");
        } catch (JSONException e) {
            this.close_quarter = "";
        }
        try {
            this.create_date = data.getString("create_date");
        } catch (JSONException e) {
            this.create_date = "";
        }
        try {
            this.create_fiscal_year = data.getString("create_fiscal_year");
        } catch (JSONException e) {
            this.create_fiscal_year = "";
        }
        try {
            this.create_month = data.getString("create_month");
        } catch (JSONException e) {
            this.create_month = "";
        }
        try {
            this.create_quarter = data.getString("create_quarter");
        } catch (JSONException e) {
            this.create_quarter = "";
        }
        try {
            this.created_by = data.getString("created_by");
        } catch (JSONException e) {
            this.created_by = "";
        }
        try {
            this.customer = data.getString("customer");
        } catch (JSONException e) {
            this.customer = "";
        }
        try {
            this.description = data.getString("description");
        } catch (JSONException e) {
            this.description = "";
        }
        try {
            this.fl = data.getString("fl");
        } catch (JSONException e) {
            this.fl = "";
        }
        try {
            this.impact = data.getString("impact");
        } catch (JSONException e) {
            this.impact = "";
        }
        try {
            this.last_updated = data.getString("last_updated");
        } catch (JSONException e) {
            this.last_updated = "";
        }
        try {
            this.last_updated_by = data.getString("last_updated_by");
        } catch (JSONException e) {
            this.last_updated_by = "";
        }
        try {
            this.location_address = data.getString("location_address");
        } catch (JSONException e) {
            this.location_address = "";
        }
        try {
            this.location_name = data.getString("location_name");
        } catch (JSONException e) {
            this.location_name = "";
        }
        try {
            this.original_priority = data.getString("original_priority");
        } catch (JSONException e) {
            this.original_priority = "";
        }
        try {
            this.owned_by_cas = data.getString("owned_by_cas");
        } catch (JSONException e) {
            this.owned_by_cas = "";
        }
        try {
            this.owner_hanle = data.getString("owner_hanle");
        } catch (JSONException e) {
            this.owner_hanle = "";
        }
        try {
            this.owner_name = data.getString("owner_name");
        } catch (JSONException e) {
            this.owner_name = "";
        }
        try {
            this.parent_name = data.getString("parent_name");
        } catch (JSONException e) {
            this.parent_name = "";
        }
        try {
            this.primary_contact_email = data.getString("primary_contact_email");
        } catch (JSONException e) {
            this.primary_contact_email = "";
        }
        try {
            this.priority = data.getString("priority");
        } catch (JSONException e) {
            this.priority = "";
        }
        try {
            this.product = data.getString("product");
        } catch (JSONException e) {
            this.product = "";
        }
        try {
            this.reassignment_count = data.getString("reassignment_count");
        } catch (JSONException e) {
            this.reassignment_count = "";
        }
        try {
            this.resolve_date = data.getString("resolve_date");
        } catch (JSONException e) {
            this.resolve_date = "";
        }
        try {
            this.response_date = data.getString("response_date");
        } catch (JSONException e) {
            this.response_date = "";
        }
        try {
            this.restore_date = data.getString("restore_date");
        } catch (JSONException e) {
            this.restore_date = "";
        }
        try {
            this.short_description = data.getString("short_description");
        } catch (JSONException e) {
            this.short_description = "";
        }
        try {
            this.status = data.getString("status");
        } catch (JSONException e) {
            this.status = "";
        }
        try {
            this.status_reason = data.getString("status_reason");
        } catch (JSONException e) {
            this.status_reason = "";
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
            this.ticket_no = data.getString("ticket_no");
        } catch (JSONException e) {
            this.ticket_no = "";
        }
        try {
            this.type = data.getString("type");
        } catch (JSONException e) {
            this.type = "";
        }
        try {
            this._class = data.getString("class");
        } catch (JSONException e) {
            this._class = "";
        }
        try {
            this.subclass = data.getString("subclass");
        } catch (JSONException e) {
            this.subclass = "";
        }

    }

    @Override
    public String toString() {
        return "Incident [ticket_no=\" + ticket_no" + "\",assignment_group=" + assignment_group
                + ", cas_subtype=" + cas_subtype + ", cas_type=" + cas_type + ", casproduct=" + casproduct
                + ", ci_name=" + ci_name + ", service_model=" + service_model + ", close_date=" + close_date
                + ", close_fiscal_year=" + close_fiscal_year + ", close_month=" + close_month + ", close_quarter="
                + close_quarter + ", create_date=" + create_date + ", create_fiscal_year=" + create_fiscal_year
                + ", create_month=" + create_month + ", create_quarter=" + create_quarter + ", created_by=" + created_by
                + ", customer=" + customer + ", description=" + description + ", fl=" + fl + ", impact=" + impact
                + ", last_updated=" + last_updated + ", last_updated_by=" + last_updated_by + ", location_address="
                + location_address + ", location_name=" + location_name + ", original_priority=" + original_priority
                + ", owned_by_cas=" + owned_by_cas + ", owner_hanle=" + owner_hanle + ", owner_name=" + owner_name
                + ", parent_name=" + parent_name + ", primary_contact_email=" + primary_contact_email + ", priority="
                + priority + ", product=" + product + ", reassignment_count=" + reassignment_count + ", resolve_date="
                + resolve_date + ", response_date=" + response_date + ", restore_date=" + restore_date
                + ", short_description=" + short_description + ", status=" + status + ", status_reason=" + status_reason
                + ", supervisor=" + supervisor + ", supervisor_handle=" + supervisor_handle + ",  type=" + type
                + ", _class=" + _class + ", subclass=" + subclass + "]";
    }

}



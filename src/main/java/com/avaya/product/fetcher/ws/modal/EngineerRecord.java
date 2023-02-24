package com.avaya.product.fetcher.ws.modal;

import java.util.List;

import com.avaya.product.fetcher.ws.SRRecord;

public class EngineerRecord {
	private List<SRRecord> listOfSRs;
    private List<SRRecord> outageSRList;
    private List<SRRecord> sbiSRList;
    private List<SRRecord> biSRList;
    private List<SRRecord> nsiSRList;
    private List<SRRecord> handoffSRList;
    private List<SRRecord> extraContributionSRList;
    private List<SRRecord> collaList;

    private float avgSiebelBIResolutionTime;
    private float avgSiebelBIRestoreTime;
    private float avgSiebelBICompletionTime;

    private int totalSiebelNSI;
    private float avgSiebelNSIResponseTime;
    private float avgSiebelNSIResolutionTime;
    private float avgSiebelNSIRestoreTime;
    private float avgSiebelNSICompletionTime;

    private int totalHandoff;
    private int Collab;

    private String engineerName;
    private String engineerHandle;
    private String managerName;
    private String managerHandle;

    private int totalSiebelOutage;
    private float avgSiebelOutageResponseTime;
    private float avgSiebelOutageResolutionTime;
    private float avgSiebelOutageRestoreTime;
    private float avgSiebelOutageCompletionTime;

    private int totalSiebelSBI;
    private float avgSiebelSBIResponseTime;
    private float avgSiebelSBIResolutionTime;
    private float avgSiebelSBIRestoreTime;
    private float avgSiebelSBICompletionTime;

    private int totalSiebelBI;
    private float avgSiebelBIResponseTime;

    private float avgResponseTime;
    private float avgRestorationTime;
    private float avgResolutionTime;
    private float avgCompletionTime;

    private int noOfMEAs;
    private float avgUtilizationHour;

    private List<SnowRecord> listSnowItem;
    private List<SnowRecord> listSnowIncident;
    private List<SnowRecord> listSnowChange;
    private List<SnowRecord> listSnowIncidentTask;
    private List<SnowRecord> listSnowChangeTask;
    private List<SnowRecord> listSnowAlarms;
    private List<SnowRecord> listSnowProjectTask;
    private List<SnowRecord> listSnowProblemTask;
    private List<SnowRecord> listSnowProblem;
    private List<SnowRecord> listSnowCritical;
    private List<SnowRecord> listSnowHigh;
    private List<SnowRecord> listSnowModerate;
    private List<SnowRecord> listSnowLow;
    private List<SnowRecord> listSnowAlarmCritical;
    private List<SnowRecord> listSnowAlarmHigh;
    private List<SnowRecord> listSnowAlarmModerate;
    private List<SnowRecord> listSnowAlarmLow;

    public List<SnowRecord> getListSnowIncident() {
        return listSnowIncident;
    }

    public void setListSnowIncident(List<SnowRecord> listSnowIncident) {
        this.listSnowIncident = listSnowIncident;
    }

    public List<SnowRecord> getListSnowChange() {
        return listSnowChange;
    }

    public void setListSnowChange(List<SnowRecord> listSnowChange) {
        this.listSnowChange = listSnowChange;
    }

    public List<SnowRecord> getListSnowIncidentTask() {
        return listSnowIncidentTask;
    }

    public void setListSnowIncidentTask(List<SnowRecord> listSnowIncidentTask) {
        this.listSnowIncidentTask = listSnowIncidentTask;
    }

    public List<SnowRecord> getListSnowChangeTask() {
        return listSnowChangeTask;
    }

    public void setListSnowChangeTask(List<SnowRecord> listSnowChangeTask) {
        this.listSnowChangeTask = listSnowChangeTask;
    }

    public List<SnowRecord> getListSnowAlarms() {
        return listSnowAlarms;
    }

    public void setListSnowAlarms(List<SnowRecord> listSnowAlarms) {
        this.listSnowAlarms = listSnowAlarms;
    }

    public List<SnowRecord> getListSnowProjectTask() {
        return listSnowProjectTask;
    }

    public void setListSnowProjectTask(List<SnowRecord> listSnowProjectTask) {
        this.listSnowProjectTask = listSnowProjectTask;
    }

    public List<SnowRecord> getListSnowProblemTask() {
        return listSnowProblemTask;
    }

    public void setListSnowProblemTask(List<SnowRecord> listSnowProblemTask) {
        this.listSnowProblemTask = listSnowProblemTask;
    }

    public List<SnowRecord> getListSnowProblem() {
        return listSnowProblem;
    }

    public void setListSnowProblem(List<SnowRecord> listSnowProblem) {
        this.listSnowProblem = listSnowProblem;
    }

    public List<SnowRecord> getListSnowCritical() {
        return listSnowCritical;
    }

    public void setListSnowCritical(List<SnowRecord> listSnowCritical) {
        this.listSnowCritical = listSnowCritical;
    }

    public List<SnowRecord> getListSnowHigh() {
        return listSnowHigh;
    }

    public void setListSnowHigh(List<SnowRecord> listSnowHigh) {
        this.listSnowHigh = listSnowHigh;
    }

    public List<SnowRecord> getListSnowModerate() {
        return listSnowModerate;
    }

    public void setListSnowModerate(List<SnowRecord> listSnowModerate) {
        this.listSnowModerate = listSnowModerate;
    }

    public List<SnowRecord> getListSnowLow() {
        return listSnowLow;
    }

    public void setListSnowLow(List<SnowRecord> listSnowLow) {
        this.listSnowLow = listSnowLow;
    }

    public List<SnowRecord> getListSnowAlarmCritical() {
        return listSnowAlarmCritical;
    }

    public void setListSnowAlarmCritical(List<SnowRecord> listSnowAlarmCritical) {
        this.listSnowAlarmCritical = listSnowAlarmCritical;
    }

    public List<SnowRecord> getListSnowAlarmHigh() {
        return listSnowAlarmHigh;
    }

    public void setListSnowAlarmHigh(List<SnowRecord> listSnowAlarmHigh) {
        this.listSnowAlarmHigh = listSnowAlarmHigh;
    }

    public List<SnowRecord> getListSnowAlarmModerate() {
        return listSnowAlarmModerate;
    }

    public void setListSnowAlarmModerate(List<SnowRecord> listSnowAlarmModerate) {
        this.listSnowAlarmModerate = listSnowAlarmModerate;
    }

    public List<SnowRecord> getListSnowAlarmLow() {
        return listSnowAlarmLow;
    }

    public void setListSnowAlarmLow(List<SnowRecord> listSnowAlarmLow) {
        this.listSnowAlarmLow = listSnowAlarmLow;
    }

    public List<SRRecord> getListOfSRs() {
        return listOfSRs;
    }

    public void setListOfSRs(List<SRRecord> listOfSRs) {
        this.listOfSRs = listOfSRs;
    }

    public List<SRRecord> getOutageSRList() {
        return outageSRList;
    }

    public void setOutageSRList(List<SRRecord> outageSRList) {
        this.outageSRList = outageSRList;
    }

    public List<SRRecord> getSbiSRList() {
        return sbiSRList;
    }

    public void setSbiSRList(List<SRRecord> sbiSRList) {
        this.sbiSRList = sbiSRList;
    }

    public List<SRRecord> getBiSRList() {
        return biSRList;
    }

    public void setBiSRList(List<SRRecord> biSRList) {
        this.biSRList = biSRList;
    }

    public List<SRRecord> getNsiSRList() {
        return nsiSRList;
    }

    public void setNsiSRList(List<SRRecord> nsiSRList) {
        this.nsiSRList = nsiSRList;
    }

    public List<SRRecord> getHandoffSRList() {
        return handoffSRList;
    }

    public void setHandoffSRList(List<SRRecord> handoffSRList) {
        this.handoffSRList = handoffSRList;
    }

    public List<SRRecord> getExtraContributionSRList() {
        return extraContributionSRList;
    }

    public void setExtraContributionSRList(List<SRRecord> extraContributionSRList) {
        this.extraContributionSRList = extraContributionSRList;
    }

    public List<SRRecord> getCollaList() {
        return collaList;
    }

    public void setCollaList(List<SRRecord> collaList) {
        this.collaList = collaList;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEngineerHandle() {
        return engineerHandle;
    }

    public void setEngineerHandle(String engineerHandle) {
        this.engineerHandle = engineerHandle;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerHandle() {
        return managerHandle;
    }

    public void setManagerHandle(String managerHandle) {
        this.managerHandle = managerHandle;
    }

    public int getTotalSiebelOutage() {
        return totalSiebelOutage;
    }

    public void setTotalSiebelOutage(int totalSiebelOutage) {
        this.totalSiebelOutage = totalSiebelOutage;
    }

    public float getAvgSiebelOutageResponseTime() {
        return avgSiebelOutageResponseTime;
    }

    public void setAvgSiebelOutageResponseTime(float avgSiebelOutageResponseTime) {
        this.avgSiebelOutageResponseTime = avgSiebelOutageResponseTime;
    }

    public float getAvgSiebelOutageResolutionTime() {
        return avgSiebelOutageResolutionTime;
    }

    public void setAvgSiebelOutageResolutionTime(float avgSiebelOutageResolutionTime) {
        this.avgSiebelOutageResolutionTime = avgSiebelOutageResolutionTime;
    }

    public float getAvgSiebelOutageRestoreTime() {
        return avgSiebelOutageRestoreTime;
    }

    public void setAvgSiebelOutageRestoreTime(float avgSiebelOutageRestoreTime) {
        this.avgSiebelOutageRestoreTime = avgSiebelOutageRestoreTime;
    }

    public float getAvgSiebelOutageCompletionTime() {
        return avgSiebelOutageCompletionTime;
    }

    public void setAvgSiebelOutageCompletionTime(float avgSiebelOutageCompletionTime) {
        this.avgSiebelOutageCompletionTime = avgSiebelOutageCompletionTime;
    }

    public int getTotalSiebelSBI() {
        return totalSiebelSBI;
    }

    public void setTotalSiebelSBI(int totalSiebelSBI) {
        this.totalSiebelSBI = totalSiebelSBI;
    }

    public float getAvgSiebelSBIResponseTime() {
        return avgSiebelSBIResponseTime;
    }

    public void setAvgSiebelSBIResponseTime(float avgSiebelSBIResponseTime) {
        this.avgSiebelSBIResponseTime = avgSiebelSBIResponseTime;
    }

    public float getAvgSiebelSBIResolutionTime() {
        return avgSiebelSBIResolutionTime;
    }

    public void setAvgSiebelSBIResolutionTime(float avgSiebelSBIResolutionTime) {
        this.avgSiebelSBIResolutionTime = avgSiebelSBIResolutionTime;
    }

    public float getAvgSiebelSBIRestoreTime() {
        return avgSiebelSBIRestoreTime;
    }

    public void setAvgSiebelSBIRestoreTime(float avgSiebelSBIRestoreTime) {
        this.avgSiebelSBIRestoreTime = avgSiebelSBIRestoreTime;
    }

    public float getAvgSiebelSBICompletionTime() {
        return avgSiebelSBICompletionTime;
    }

    public void setAvgSiebelSBICompletionTime(float avgSiebelSBICompletionTime) {
        this.avgSiebelSBICompletionTime = avgSiebelSBICompletionTime;
    }

    public int getTotalSiebelBI() {
        return totalSiebelBI;
    }

    public void setTotalSiebelBI(int totalSiebelBI) {
        this.totalSiebelBI = totalSiebelBI;
    }

    public float getAvgSiebelBIResponseTime() {
        return avgSiebelBIResponseTime;
    }

    public void setAvgSiebelBIResponseTime(float avgSiebelBIResponseTime) {
        this.avgSiebelBIResponseTime = avgSiebelBIResponseTime;
    }

    public float getAvgSiebelBIResolutionTime() {
        return avgSiebelBIResolutionTime;
    }

    public void setAvgSiebelBIResolutionTime(float avgSiebelBIResolutionTime) {
        this.avgSiebelBIResolutionTime = avgSiebelBIResolutionTime;
    }

    public float getAvgSiebelBIRestoreTime() {
        return avgSiebelBIRestoreTime;
    }

    public void setAvgSiebelBIRestoreTime(float avgSiebelBIRestoreTime) {
        this.avgSiebelBIRestoreTime = avgSiebelBIRestoreTime;
    }

    public float getAvgSiebelBICompletionTime() {
        return avgSiebelBICompletionTime;
    }

    public void setAvgSiebelBICompletionTime(float avgSiebelBICompletionTime) {
        this.avgSiebelBICompletionTime = avgSiebelBICompletionTime;
    }

    public int getTotalSiebelNSI() {
        return totalSiebelNSI;
    }

    public void setTotalSiebelNSI(int totalSiebelNSI) {
        this.totalSiebelNSI = totalSiebelNSI;
    }

    public float getAvgSiebelNSIResponseTime() {
        return avgSiebelNSIResponseTime;
    }

    public void setAvgSiebelNSIResponseTime(float avgSiebelNSIResponseTime) {
        this.avgSiebelNSIResponseTime = avgSiebelNSIResponseTime;
    }

    public float getAvgSiebelNSIResolutionTime() {
        return avgSiebelNSIResolutionTime;
    }

    public void setAvgSiebelNSIResolutionTime(float avgSiebelNSIResolutionTime) {
        this.avgSiebelNSIResolutionTime = avgSiebelNSIResolutionTime;
    }

    public float getAvgSiebelNSIRestoreTime() {
        return avgSiebelNSIRestoreTime;
    }

    public void setAvgSiebelNSIRestoreTime(float avgSiebelNSIRestoreTime) {
        this.avgSiebelNSIRestoreTime = avgSiebelNSIRestoreTime;
    }

    public float getAvgSiebelNSICompletionTime() {
        return avgSiebelNSICompletionTime;
    }

    public void setAvgSiebelNSICompletionTime(float avgSiebelNSICompletionTime) {
        this.avgSiebelNSICompletionTime = avgSiebelNSICompletionTime;
    }

    public void setAvgResponseTime(float avgResolutionTime) {
        this.avgResponseTime = avgResolutionTime;
    }

    public float getAvgResponseTime() {
        return this.avgResponseTime;
    }

    //Restoration
    public void setAvgRestorationTime(float avgRestorationTime) {
        this.avgRestorationTime = (float) (Math.round(avgRestorationTime * 100.0) / 100.0);
    }
    public float getAvgRestorationTime() {
        return avgRestorationTime;
    }

    //Resolution
    public void setAvgResolutionTime(float avgResolutionTime) {
        this.avgResolutionTime = (float) (Math.round(avgResolutionTime * 100.0) / 100.0);
    }
    public float getAvgResolutionTime() {
        return avgResolutionTime;
    }

    //Completion
    public void setAvgCompletionTime(float avgCompletionTime) {
        this.avgCompletionTime= (float) (Math.round(avgCompletionTime * 100.0) / 100.0);
    }

    public float getAvgCompletionTime() {
        return avgCompletionTime;
    }

    public void setMEAs(int noOfMEAs) {
        this.noOfMEAs = noOfMEAs;
    }

    public float getUtilizationHour() {
        return avgUtilizationHour;
    }

    public void setUtilizationHour(float utilizationHour) {
        this.avgUtilizationHour = utilizationHour;
    }

    public int getMEAs() {
        return noOfMEAs;
    }

    public int getTotalHandoff() {
        return totalHandoff;
    }

    public void setTotalHandoff(int totalHandoff) {
        this.totalHandoff = totalHandoff;
    }

    public int getCollab() {
        return Collab;
    }

    public void setCollab(int collab) {
        Collab = collab;
    }

    public List<SnowRecord> getListSnowItem() {
        return listSnowItem;
    }

    public void setListSnowItem(List<SnowRecord> listSnowItem) {
        this.listSnowItem = listSnowItem;
    }
}

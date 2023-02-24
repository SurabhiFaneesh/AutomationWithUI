package com.avaya.product.fetcher.ws;
import java.util.ArrayList;

public class TimeRecordList extends ArrayList<TimeRecord> {
    static final long serialVersionUID = 1L;

    public Double getTotalTime() {
        Double totalTime = Double.valueOf(0.0D);
        for (TimeRecord timerec : this)
            totalTime = Double.valueOf(totalTime.doubleValue() + timerec.getHours().doubleValue());
        return totalTime;
    }
}

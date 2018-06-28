package com.wardabbass.hereassignment.models;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class TaxiEta implements Comparable<TaxiEta> {
    private String name;
    private String imageUrl;
    private DateTime eta;
    private String diffTimeFromNow;

    public TaxiEta(String name, String imageUrl, DateTime eta) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.eta = eta;
        calculateDateDiffFromNow();
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void calculateDateDiffFromNow() {
        StringBuilder etaStringRep = new StringBuilder();
        DateTime now = DateTime.now();
        Period p = new Period(now, eta);


        long standardHours = p.getHours();
        if (standardHours >= 1) {
            etaStringRep.append(standardHours)
                    .append("h")
                    .append(" ");
        }
        long standardMinutes = p.getMinutes();
        if (standardMinutes < 60 &&  standardMinutes >0) {
            etaStringRep.append(standardMinutes)
                    .append("m")
                    .append(" ");
        }
        long standardSeconds = p.getSeconds();
        if (standardSeconds < 60 && standardSeconds > 0) {
            etaStringRep.append(standardSeconds)
                    .append("s")
                    .append(" ");
        }

        this.diffTimeFromNow=etaStringRep.toString();
    }

    public void setEta(DateTime eta) {
        this.eta = eta;
        calculateDateDiffFromNow();
    }

    public String getDateDifFromNow() {
        return diffTimeFromNow;
    }

    @Override
    public int compareTo(@NonNull TaxiEta o) {
        return eta.compareTo(o.eta);
    }
}

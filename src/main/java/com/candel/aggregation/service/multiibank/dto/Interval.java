package com.candel.aggregation.service.multiibank.dto;

public enum Interval {

    M1(60, "1m");

    public final int seconds;
    public final String name;

    Interval(int seconds, String name) {
        this.seconds = seconds;
        this.name = name;
    }

    public static Interval fromName(String name){
        for (Interval interval : Interval.values()) {
            if(interval.name.equals(name)){
                return interval;
            }
        }
        return null;
    }

}

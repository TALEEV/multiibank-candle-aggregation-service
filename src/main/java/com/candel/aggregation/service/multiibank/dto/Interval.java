package com.candel.aggregation.service.multiibank.dto;

public enum Interval {

    S1(1, "1s"),
    S5(5, "5s"),
    M1(60, "1m"),
    M15(900, "15m"),
    H1(3600, "1h");

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

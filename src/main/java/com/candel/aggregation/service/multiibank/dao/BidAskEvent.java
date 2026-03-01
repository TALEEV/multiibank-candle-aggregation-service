package com.candel.aggregation.service.multiibank.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BidAskEvent {
    private String symbol;
    private double bid;
    private double ask;
    private long timestamp;
}

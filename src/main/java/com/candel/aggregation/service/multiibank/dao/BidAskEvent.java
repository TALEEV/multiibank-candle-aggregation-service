package com.candel.aggregation.service.multiibank.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BidAskEvent {
    private String symbol;
    private double bid;
    private double ask;
    private long timestamp;
}

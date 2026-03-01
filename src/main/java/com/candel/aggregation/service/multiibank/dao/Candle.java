package com.candel.aggregation.service.multiibank.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Candle {
     private String symbol;
     private double open;
     private double high;
     private double low;
     private double close;
     private long timestamp;
     private double volume;
}

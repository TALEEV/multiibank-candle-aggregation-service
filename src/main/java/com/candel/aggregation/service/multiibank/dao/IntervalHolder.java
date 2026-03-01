package com.candel.aggregation.service.multiibank.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class IntervalHolder {
    public Map<Long, Candle> intervalAggregators = new ConcurrentHashMap<>();
}

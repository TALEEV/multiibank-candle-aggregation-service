package com.candel.aggregation.service.multiibank.dao;

import com.candel.aggregation.service.multiibank.dto.Interval;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class SymbolHolder {
    Map<Interval, IntervalHolder> symbolAggregators = new ConcurrentHashMap<>();
}

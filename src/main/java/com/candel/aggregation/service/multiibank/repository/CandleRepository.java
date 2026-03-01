package com.candel.aggregation.service.multiibank.repository;

import com.candel.aggregation.service.multiibank.dao.SymbolHolder;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class CandleRepository {

    public static final ConcurrentMap<String, SymbolHolder> memory = new ConcurrentHashMap<>();

}

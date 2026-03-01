package com.candel.aggregation.service.multiibank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CandleResponse {

    private String symbol;
    private List<Long> t = new ArrayList<>();
    private List<Double> o = new ArrayList<>();
    private List<Double> h = new ArrayList<>();
    private List<Double> l = new ArrayList<>();
    private List<Double> c = new ArrayList<>();
    private List<Double> v = new ArrayList<>();
}

package com.candel.aggregation.service.multiibank.util;

import com.candel.aggregation.service.multiibank.dao.*;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.repository.CandleRepository;

import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MarketDataAggregator {

    public static void aggregate(BidAskEvent bidAskEvent) {

        for (Interval interval : Interval.values()) {

            long bucket = bidAskEvent.getTimestamp() / interval.seconds;
            double price = (bidAskEvent.getBid() + bidAskEvent.getAsk())/2;

            SymbolHolder symbolHolder = CandleRepository.memory.get(bidAskEvent.getSymbol());

            if(symbolHolder == null){
                symbolHolder = new SymbolHolder();
                CandleRepository.memory.put(bidAskEvent.getSymbol(), symbolHolder);
            }

            IntervalHolder intervalHolder = symbolHolder.getSymbolAggregators().get(interval);

            if(intervalHolder == null){
                intervalHolder = new IntervalHolder();
                symbolHolder.getSymbolAggregators().put(interval, intervalHolder);
            }

            Map<Long, Candle> intervalAggregators = intervalHolder
                    .getIntervalAggregators();
            Candle currentCandle = intervalAggregators.get(bucket);

            if (currentCandle == null || currentCandle.getTimestamp()/interval.seconds != bucket) {
                currentCandle = new Candle(bidAskEvent.getSymbol(), price, price, price, price, bidAskEvent.getTimestamp(), 1);
                intervalAggregators.put(bucket, currentCandle);
            } else {
                currentCandle.setHigh(max(currentCandle.getHigh(), price));
                currentCandle.setLow(min(currentCandle.getLow(), price));
                currentCandle.setClose(price);
                currentCandle.setVolume(currentCandle.getVolume() + 1);
                intervalAggregators.put(bucket, currentCandle);
            }

        }
    }
}

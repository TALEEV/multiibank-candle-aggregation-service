package com.candel.aggregation.service.multiibank.util;

import com.candel.aggregation.service.multiibank.dao.*;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
@AllArgsConstructor
public class MarketDataAggregator {

    private final CandleRepo candleRepo;

    public void aggregate(BidAskEvent bidAskEvent) {

        for (Interval interval : Interval.values()) {

            long bucket = bidAskEvent.getTimestamp() / interval.seconds;
            double price = (bidAskEvent.getBid() + bidAskEvent.getAsk())/2;

            CandleEntity currentCandle =  candleRepo.findBySymbolAndSymbolIntervalAndBucketId(bidAskEvent.getSymbol(), interval.name, bucket);

            if (currentCandle == null || currentCandle.getTimestamp()/interval.seconds != bucket) {
                currentCandle = CandleEntity.builder()
                        .bucketId(bucket)
                        .timestamp(bidAskEvent.getTimestamp())
                        .symbol(bidAskEvent.getSymbol())
                        .symbolInterval(interval.name)
                        .low(price)
                        .open(price)
                        .close(price)
                        .high(price)
                        .volume(1.0).build();
                candleRepo.save(currentCandle);
                System.out.println("timestamp , interval: "+bidAskEvent.getTimestamp() + " , " + interval.name);
            } else {
                currentCandle.setHigh(max(currentCandle.getHigh(), price));
                currentCandle.setLow(min(currentCandle.getLow(), price));
                currentCandle.setClose(price);
                currentCandle.setVolume(currentCandle.getVolume() + 1);
                candleRepo.save(currentCandle);
            }


        }
    }
}

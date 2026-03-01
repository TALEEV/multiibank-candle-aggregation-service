package com.candel.aggregation.service.multiibank.util;

import com.candel.aggregation.service.multiibank.dao.BidAskEvent;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class MarketDataAggregator {

    private final CandleRepo candleRepo;

    @Transactional
    public void aggregate(BidAskEvent bidAskEvent) {

        for (Interval interval : Interval.values()) {

            aggregateInterval(bidAskEvent, interval);
        }
    }

    public void aggregateInterval(BidAskEvent bidAskEvent, Interval interval) {
        long bucket = bidAskEvent.getTimestamp() / interval.seconds;
        double price = (bidAskEvent.getBid() + bidAskEvent.getAsk())/2;

        int updated = candleRepo.updateCandle(bidAskEvent.getSymbol(), interval.name, bucket, price);

        if (updated == 0) {
            CandleEntity currentCandle = CandleEntity.builder()
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
            System.out.println("timestamp , interval, symbol: "+ bidAskEvent.getTimestamp() + " , " + interval.name+ " , " + bidAskEvent.getSymbol());
        }
    }
}

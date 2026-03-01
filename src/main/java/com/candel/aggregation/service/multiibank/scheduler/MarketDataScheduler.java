package com.candel.aggregation.service.multiibank.scheduler;

import com.candel.aggregation.service.multiibank.dao.BidAskEvent;
import com.candel.aggregation.service.multiibank.util.MarketDataAggregator;
import com.candel.aggregation.service.multiibank.util.MarketDataGenerator;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class MarketDataScheduler {

    private final MarketDataAggregator marketDataAggregator;

    @Scheduled(fixedDelayString = "1", timeUnit = TimeUnit.SECONDS)
    public void schedule() {
        List<BidAskEvent> events =  MarketDataGenerator.getEvents();
        for (BidAskEvent event : events) {
            marketDataAggregator.aggregate(event);
        }
    }
}

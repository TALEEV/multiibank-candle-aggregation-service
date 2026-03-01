package com.candel.aggregation.service.multiibank.util;

import com.candel.aggregation.service.multiibank.dao.BidAskEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MarketDataGenerator {

    public static List<BidAskEvent> getEvents() {

        List<String> symbols = List.of(
                "BTC-USD",
                "ETH-USD",
                "AAPL",
                "TSLA",
                "EUR-USD"
        );

        List<BidAskEvent> events = new ArrayList<>();

        long timestamp = System.currentTimeMillis() / 1000;
        System.out.println("timestamp: "+timestamp);
        int ticksPerSecond = 5;

        Map<String, Double> basePrices = Map.of(
                "BTC-USD", 30000.0,
                "ETH-USD", 2000.0,
                "AAPL", 180.0,
                "TSLA", 250.0,
                "EUR-USD", 1.10
        );


        for (String symbol : symbols) {

            double base = basePrices.get(symbol);

            for (int i = 0; i < ticksPerSecond; i++) {
                double delta = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
                double mid = base + delta;

                double spread = ThreadLocalRandom.current().nextDouble(0.01, 0.05);
                double bid = mid - spread / 2;
                double ask = mid + spread / 2;

                events.add(new BidAskEvent(symbol, bid, ask, timestamp));
            }
        }

        return events;
    }
}

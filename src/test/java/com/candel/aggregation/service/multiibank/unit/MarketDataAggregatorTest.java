package com.candel.aggregation.service.multiibank.unit;

import com.candel.aggregation.service.multiibank.dao.BidAskEvent;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import com.candel.aggregation.service.multiibank.util.MarketDataAggregator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketDataAggregatorTest {

    @InjectMocks
    private MarketDataAggregator marketDataAggregator;

    @Mock
    private CandleRepo candleRepo;


    @Test
    public void testAggregateWhenBucketDoesNotExist() {

        when(candleRepo.findBySymbolAndSymbolIntervalAndBucketId(anyString(), anyString(), anyLong()))
                .thenReturn(null);

        BidAskEvent bidAskEvent = BidAskEvent.builder()
                .symbol("BTC-USD")
                .bid(100000.0)
                .ask(100000.0)
                .timestamp(1772335565L)
                .build();

        marketDataAggregator.aggregate(bidAskEvent);

        verify(candleRepo).save(argThat(candle -> candle.getVolume() == 1.0));

    }

    @Test
    public void testAggregateWhenBucketExist() {

        CandleEntity candleEntity = CandleEntity.builder()
                .bucketId(1L)
                .timestamp(1772335565L)
                .symbol("BTC-USD")
                .symbolInterval("1m")
                .open(50000.0)
                .high(51000.0)
                .low(49000.0)
                .close(50500.0)
                .volume(100.0)
                .build();

        when(candleRepo.findBySymbolAndSymbolIntervalAndBucketId(anyString(), anyString(), anyLong()))
                .thenReturn(candleEntity);

        BidAskEvent bidAskEvent = BidAskEvent.builder()
                .symbol("BTC-USD")
                .bid(100000.0)
                .ask(100000.0)
                .timestamp(1772335565L)
                .build();

        marketDataAggregator.aggregate(bidAskEvent);

        verify(candleRepo).save(argThat(candle -> candle.getVolume() == 101.0));

    }
}

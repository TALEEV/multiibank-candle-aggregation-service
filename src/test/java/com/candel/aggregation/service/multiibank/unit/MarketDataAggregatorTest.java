package com.candel.aggregation.service.multiibank.unit;

import com.candel.aggregation.service.multiibank.dao.BidAskEvent;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import com.candel.aggregation.service.multiibank.util.MarketDataAggregator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarketDataAggregatorTest {

    @InjectMocks
    private MarketDataAggregator marketDataAggregator;

    @Mock
    private CandleRepo candleRepo;


    @Test
    public void testAggregateWhenBucketDoesNotExist() {
        when(candleRepo.updateCandle(anyString(), anyString(), anyLong(), anyDouble())).thenReturn(0);

        BidAskEvent event = BidAskEvent.builder()
                .symbol("BTC-USD")
                .bid(100.0)
                .ask(100.0)
                .timestamp(1000L)
                .build();

        marketDataAggregator.aggregateInterval(event, Interval.S1);

        verify(candleRepo).updateCandle("BTC-USD", "1s", 1000L, 100.0);
        verify(candleRepo).save(argThat(candle ->
                candle.getSymbol().equals("BTC-USD") &&
                        candle.getSymbolInterval().equals("1s") &&
                        candle.getBucketId() == 1000L &&
                        candle.getVolume() == 1.0 &&
                        candle.getOpen() == 100.0
        ));
    }

    @Test
    public void testAggregateWhenBucketExist() {
        when(candleRepo.updateCandle(anyString(), anyString(), anyLong(), anyDouble())).thenReturn(1);

        BidAskEvent event = BidAskEvent.builder()
                .symbol("BTC-USD")
                .bid(100.0)
                .ask(100.0)
                .timestamp(1000L)
                .build();

        marketDataAggregator.aggregateInterval(event, Interval.S1);

        verify(candleRepo).updateCandle("BTC-USD", "1s", 1000L, 100.0);
        verify(candleRepo, never()).save(any());
    }

}

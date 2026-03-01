package com.candel.aggregation.service.multiibank.facade;

import com.candel.aggregation.service.multiibank.dao.Candle;
import com.candel.aggregation.service.multiibank.dto.CandleResponse;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.repository.CandleRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class AggregationFacade {

    public CandleResponse getMarketDataHistory(@RequestParam String symbol,
                                                     @RequestParam String interval,
                                                     @RequestParam long from,
                                                     @RequestParam long to){
        try{
            List<Candle> candles =  CandleRepository.memory.get(symbol).getSymbolAggregators().get(Interval.fromName(interval)).getIntervalAggregators()
                    .values().stream().
                    filter((candle) -> candle.getTimestamp() >= from && candle.getTimestamp() <= to)
                    .toList();

            CandleResponse candleResponse = new CandleResponse();
            candleResponse.setSymbol(symbol);
            candles.forEach(candle -> {
                candleResponse.getT().add(candle.getTimestamp());
                candleResponse.getO().add(candle.getOpen());
                candleResponse.getC().add(candle.getClose());
                candleResponse.getH().add(candle.getHigh());
                candleResponse.getL().add(candle.getLow());
                candleResponse.getV().add(candle.getVolume());
            });
            return candleResponse;
        }catch (Exception e){
            return null;
        }

    }
}

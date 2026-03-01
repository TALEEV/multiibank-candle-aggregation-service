package com.candel.aggregation.service.multiibank.facade;

import com.candel.aggregation.service.multiibank.dao.Candle;
import com.candel.aggregation.service.multiibank.dto.CandleResponse;
import com.candel.aggregation.service.multiibank.dto.Interval;
import com.candel.aggregation.service.multiibank.mapper.CandleMapper;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AggregationFacade {

    private final CandleRepo candleRepo;

    private final CandleMapper candleMapper;

    public CandleResponse getMarketDataHistory(String symbol,
                                               String interval,
                                               long from,
                                               long to){
        try{
            List<Candle> candles =  CandleRepository.memory.get(symbol).getSymbolAggregators().get(Interval.fromName(interval)).getIntervalAggregators()
                    .values().stream().
                    filter((candle) -> candle.getTimestamp() >= from && candle.getTimestamp() <= to)
                    .toList();

            return getCandleResponse(symbol, candles);
        }catch (Exception e){
            return null;
        }
    }

    private static CandleResponse getCandleResponse(String symbol, List<Candle> candles) {
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
    }

    public CandleResponse getMarketDataHistoryFromDatabase(String symbol,
                                               String interval,
                                               long from,
                                               long to){
        List<CandleEntity> candles = candleRepo.findBySymbolAndSymbolIntervalAndTimestampBetween(symbol, interval, from, to);
        if(!candles.isEmpty()){
            List<Candle> candleList = candleMapper.mapCandleList(candles);
            return getCandleResponse(symbol, candleList);
        }else{
            return null;
        }


    }
}

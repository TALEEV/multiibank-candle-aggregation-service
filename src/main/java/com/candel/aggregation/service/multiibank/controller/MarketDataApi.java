package com.candel.aggregation.service.multiibank.controller;

import com.candel.aggregation.service.multiibank.dao.Candle;
import com.candel.aggregation.service.multiibank.dto.CandleResponse;
import com.candel.aggregation.service.multiibank.facade.AggregationFacade;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
public class MarketDataApi {


    private final AggregationFacade aggregationFacade;

    @GetMapping("")
    public ResponseEntity<@NonNull CandleResponse> getMarketDataHistory(@RequestParam String symbol,
                                                                        @RequestParam String interval,
                                                                        @RequestParam long from,
                                                                        @RequestParam long to){
        CandleResponse candleResponse =  aggregationFacade.getMarketDataHistory(symbol, interval, from, to);
        if(candleResponse == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Market data not found for symbol: " + symbol
            );
        }
        return new ResponseEntity<>(candleResponse, HttpStatus.OK);

    }
}

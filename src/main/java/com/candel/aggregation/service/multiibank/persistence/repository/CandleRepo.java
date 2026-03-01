package com.candel.aggregation.service.multiibank.persistence.repository;

import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandleRepo extends JpaRepository<CandleEntity, Long>, PagingAndSortingRepository<CandleEntity, Long>, JpaSpecificationExecutor<CandleEntity> {

    List<CandleEntity> findBySymbolAndSymbolIntervalAndTimestampBetween(String symbol, String interval, long start, long end);

    @Modifying
    @Query("UPDATE CandleEntity c SET c.high = CASE WHEN :price > c.high THEN :price ELSE c.high END, " +
           "c.low = CASE WHEN :price < c.low THEN :price ELSE c.low END, " +
           "c.close = :price, c.volume = c.volume + 1 " +
           "WHERE c.symbol = :symbol AND c.symbolInterval = :interval AND c.bucketId = :bucketId")
    int updateCandle(@Param("symbol") String symbol, @Param("interval") String interval,
                     @Param("bucketId") Long bucketId, @Param("price") Double price);
}

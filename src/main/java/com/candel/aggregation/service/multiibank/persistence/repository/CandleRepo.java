package com.candel.aggregation.service.multiibank.persistence.repository;

import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CandleRepo extends JpaRepository<CandleEntity, Long>, PagingAndSortingRepository<CandleEntity, Long>, JpaSpecificationExecutor<CandleEntity> {

    List<CandleEntity> findBySymbolAndSymbolIntervalAndTimestampBetween(String symbol, String interval, long start, long end);

    CandleEntity findBySymbolAndSymbolIntervalAndBucketId(String symbol, String interval, long bucket);
}

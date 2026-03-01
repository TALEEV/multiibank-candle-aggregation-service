package com.candel.aggregation.service.multiibank.mapper;

import com.candel.aggregation.service.multiibank.dao.Candle;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CandleMapper {

   Candle mapCandle(CandleEntity candleEntity );

   List<Candle> mapCandleList(List<CandleEntity> candleEntityList );

}

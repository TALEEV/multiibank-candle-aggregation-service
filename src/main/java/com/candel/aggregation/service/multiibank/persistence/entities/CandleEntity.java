package com.candel.aggregation.service.multiibank.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "candle",
        uniqueConstraints = @UniqueConstraint(columnNames = {"symbol", "symbolInterval", "bucketId"}),
        indexes = @Index(name = "idx_symbol_interval_bucket", columnList = "symbol, symbolInterval, timestamp")
)
@ToString
public class CandleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bucketId")
    private Long bucketId;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "symbolInterval")
    private String symbolInterval;

    @Column(name = "open")
    private Double open;

    @Column(name = "high")
    private Double high;

    @Column(name = "low")
    private Double low;

    @Column(name = "close")
    private Double close;

    private Double volume;

}

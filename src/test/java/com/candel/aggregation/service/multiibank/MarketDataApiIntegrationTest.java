package com.candel.aggregation.service.multiibank;

import com.candel.aggregation.service.multiibank.dto.CandleResponse;
import com.candel.aggregation.service.multiibank.persistence.entities.CandleEntity;
import com.candel.aggregation.service.multiibank.persistence.repository.CandleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class MarketDataApiIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CandleRepo candleRepo;

    @BeforeEach
    void setUp() {
        candleRepo.deleteAll();

        candleRepo.save(CandleEntity.builder()
                .bucketId(1L)
                .timestamp(1772335565L)
                .symbol("BTC-USD")
                .symbolInterval("1m")
                .open(50000.0)
                .high(51000.0)
                .low(49000.0)
                .close(50500.0)
                .volume(100.0)
                .build());
    }

    @Test
    void shouldReturnCandleDataWhenValidRequest() {
        String url = "http://localhost:" + port + "/history?symbol=BTC-USD&interval=1m&from=1772335565&to=1772335565";

        ResponseEntity<CandleResponse> response = restTemplate.getForEntity(url, CandleResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("BTC-USD", response.getBody().getSymbol());
        assertEquals(1, response.getBody().getT().size());
        assertEquals(1772335565L, response.getBody().getT().get(0));
    }

    @Test
    void shouldReturn404WhenNoDataFound() {
        String url = "http://localhost:" + port + "/history?symbol=ETH-USD&interval=1m&from=1772335565&to=1772335565";

        try {
            restTemplate.getForEntity(url, CandleResponse.class);
            fail("Expected HttpClientErrorException");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}

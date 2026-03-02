# Simple-H2-database setup

## Assumptions

- **Price calculation:**  
  `price = (bidding_price - asking_price) / 2`
- **Supported intervals:**  
  `1s`, `5s`, `1m`, `15m`, `1h` as per requirements.

## Tradeoffs:
    if new "interval" is to be added a change in the code is required

## Future improvements:
    - Need to add unit tests
    - modify the code to accomodate new intervals in subsequent commits
## Instructions to run:
    Prerequisites:
        Java 17
        Maven 3.8.1
        Spring Boot 4.0.3
    To build:
        mvn clean install
    To run integeration/unit tests:
        mvn test
    mvn spring-boot:run
    To run:
        mvn spring-boot:run
    To Test:
        curl --location 'http://localhost:8080/history?symbol=BTC-USD&interval=1m&from=1772335565&to=1772338565'
        make sure to pass "from" in seconds, you can get the relevent timestamps from the application logs
        as i am printing the timestamp for new bucket for each symbol and interval
        
        you can pass the value of "to" same as "from" or you can add some more seconds to it
        e.g : from  = 1772338975, to = 1772338975 + 10 = 1772338985
        will return the candles for the last 10 seconds

## Note:
    I used Spring boot scheduler to generate 5 ticks per second for five different symbols
    with dynamically generate ask and bid prices.
    
    Each event get sent to in memory aggreagator and the aggregated candle gets generated
    and get saved to the h2 database

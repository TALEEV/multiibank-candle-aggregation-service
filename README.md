Simple-concurrent-memory setup

Assumptions:
    price = (bidding_price - asking_price)/2
    intervals = 1s, 5s, 1m, 15, 1h as specified in the requirements
Tradeoffs:
    if new "interval" is to be added a change in the code is required
    I will modify the code to accomodate new intervals in subsequent commits
Instructions to run:
    This project use Java/Maven/Spring Boot
To build:
    mvn clean install
To run:
    mvn spring-boot:run
To Test:
    curl --location 'http://localhost:8080/history?symbol=BTC-USD&interval=1m&from=1772335565&to=1772338565'

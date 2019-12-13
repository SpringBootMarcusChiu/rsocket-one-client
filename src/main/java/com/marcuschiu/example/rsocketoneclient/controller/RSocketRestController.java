package com.marcuschiu.example.rsocketoneclient.controller;

import com.marcuschiu.example.rsocketoneclient.model.MarketDataModel;
import com.marcuschiu.example.rsocketoneclient.model.MarketDataRequest;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RSocketRestController {

    private Integer marketDataNumber = 0;

    // using @Bean defined in RSocketOneClientApplication.java
    @Autowired
    private RSocketRequester rSocketRequester;

    @GetMapping(value = "/get/{id}")
    public Publisher<MarketDataModel> getMarketData(@PathVariable("id") Integer id) {
        return rSocketRequester
                .route("getMarketData") // server's @MessageMapping("getMarketData")
                .data(new MarketDataRequest(id))
                .retrieveMono(MarketDataModel.class);
    }

    @GetMapping(value = "/add", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<MarketDataModel> add() {
        // add
        marketDataNumber++;
        Publisher<Void> pv = rSocketRequester
                .route("uploadMarketData") // server's @MessageMapping("uploadMarketData")
                .data(new MarketDataModel(marketDataNumber, "added market data", null))
                .send();

        // get all
        return rSocketRequester
                .route("allMarketData") // server's @MessageMapping("allMarketData")
//                .data(new MarketDataRequest(-1))
                .retrieveFlux(MarketDataModel.class);
    }
}
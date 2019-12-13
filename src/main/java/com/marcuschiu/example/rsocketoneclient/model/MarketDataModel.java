package com.marcuschiu.example.rsocketoneclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDataModel {
    Integer id;
    String description;
    String errorResponse;

    public static MarketDataModel fromException(Exception e) {
        return new MarketDataModel(null, null, e.toString());
    }
}

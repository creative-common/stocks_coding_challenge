package com.rbc.stocksapi.services;

import com.rbc.stocksapi.models.DowJonesIndex;
import com.rbc.stocksapi.models.DowJonesIndexDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IStockService {
    CompletableFuture<List<DowJonesIndexDTO>> getStocksByTickerAndQuarter(String stockTicker, Integer quarter);

    CompletableFuture<DowJonesIndexDTO> addStockTicker(DowJonesIndexDTO dowJonesIndexDTO);

    void addStockTickers(List<DowJonesIndex> dowJonesIndices);
}

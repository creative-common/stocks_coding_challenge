package com.rbc.stocksapi.services;

import com.rbc.stocksapi.mappers.IMapper;
import com.rbc.stocksapi.models.DowJonesIndex;
import com.rbc.stocksapi.models.DowJonesIndexDTO;
import com.rbc.stocksapi.repositories.StockRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StockService implements IStockService{
    private final StockRepository stockRepository;

    private final IMapper mapper = Mappers.getMapper(IMapper.class);

    @Autowired
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Async("taskExecutor")
    public CompletableFuture<List<DowJonesIndexDTO>> getStocksByTickerAndQuarter(String stockTicker, Integer quarter) {
        return CompletableFuture.completedFuture(stockRepository.findByStockAndQuarterOrderById(stockTicker, quarter))
                .thenApply(mapper::map);
    }

    @Async("taskExecutor")
    public CompletableFuture<DowJonesIndexDTO> addStockTicker(DowJonesIndexDTO dowJonesIndexDTO) {
        return CompletableFuture.completedFuture(stockRepository.save(mapper.map(dowJonesIndexDTO))).thenApply(mapper::map);
    }

    @Async("taskExecutor")
    public void addStockTickers(List<DowJonesIndex> dowJonesIndices) {
         CompletableFuture.completedFuture(stockRepository.saveAll(dowJonesIndices));
    }
}

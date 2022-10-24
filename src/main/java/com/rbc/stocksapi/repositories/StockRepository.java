package com.rbc.stocksapi.repositories;

import com.rbc.stocksapi.models.DowJonesIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<DowJonesIndex, Long> {
    List<DowJonesIndex> findByStockAndQuarterOrderById(String stock, Integer quarter);
}

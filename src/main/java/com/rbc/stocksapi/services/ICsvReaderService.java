package com.rbc.stocksapi.services;

import com.opencsv.exceptions.CsvException;
import com.rbc.stocksapi.models.DowJonesIndex;
import com.rbc.stocksapi.models.DowJonesIndexDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICsvReaderService {
    CompletableFuture<List<DowJonesIndex>> read(MultipartFile file) throws IOException, CsvException;

    DowJonesIndexDTO parseLine(String line);
}

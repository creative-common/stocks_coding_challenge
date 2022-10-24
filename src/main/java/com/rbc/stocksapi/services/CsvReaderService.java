package com.rbc.stocksapi.services;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.rbc.stocksapi.exceptions.GlobalExceptionHandler;
import com.rbc.stocksapi.mappers.IMapper;
import com.rbc.stocksapi.models.DowJonesIndex;
import com.rbc.stocksapi.models.DowJonesIndexDTO;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CsvReaderService implements ICsvReaderService{

    private final IMapper mapper = Mappers.getMapper(IMapper.class);

    private static final Logger log = LoggerFactory.getLogger(CsvReaderService.class);

    @Override
    public CompletableFuture<List<DowJonesIndex>> read(MultipartFile file) throws IOException, CsvException {

        int counter = 0;
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .build();
        List<String[]> allData = csvReader.readAll();
        List<DowJonesIndex> dowJonesIndexList = new ArrayList<>();
        for (String[] row : allData) {
            counter++;
            for (String line : row) {
                if(line != null){
                    DowJonesIndexDTO dowJonesIndexDTO = parseLine(line);
                    //Skip the line if the data is empty for following column
                    if(dowJonesIndexDTO.getStock() != null && dowJonesIndexDTO.getQuarter() != null && dowJonesIndexDTO.getDate() != null){
                        DowJonesIndex current = mapper.map(dowJonesIndexDTO);
                        dowJonesIndexList.add(current);

                    }else{
                        log.error("Stock or Date or Quarter found as null at line - " + counter + 1);
                    }
                }
            }
        }
        return CompletableFuture.supplyAsync(() -> dowJonesIndexList);
    }


    //This code could be moved to custom mapper in mapstruct for null and check empty check - for most robust performance
    @Override
    public DowJonesIndexDTO parseLine(String line) {
        List<String> data = Arrays.asList(line.split(","));
        return DowJonesIndexDTO.builder()
                .quarter(checkForNullAndEmpty(data.get(0)))
                .stock(checkForNullAndEmpty(data.get(1)))
                .date(checkForNullAndEmpty(data.get(2)))
                .open(checkForNullAndEmpty(data.get(3).substring(1)))
                .high(checkForNullAndEmpty(data.get(4).substring(1)))
                .low(checkForNullAndEmpty(data.get(5).substring(1)))
                .close(checkForNullAndEmpty(data.get(6).substring(1)))
                .volume(checkForNullAndEmpty(data.get(7)))
                .percent_change_price(checkForNullAndEmpty(data.get(8)))
                .percent_change_volume_over_last_wk(checkForNullAndEmpty(data.get(9)))
                .previous_weeks_volume(checkForNullAndEmpty(data.get(10)))
                .next_weeks_open(checkForNullAndEmpty(data.get(11).substring(1)))
                .next_weeks_close(checkForNullAndEmpty(data.get(12).substring(1)))
                .percent_change_next_weeks_price(checkForNullAndEmpty(data.get(13)))
                .days_to_next_dividend(checkForNullAndEmpty(data.get(14)))
                .percent_return_next_dividend(checkForNullAndEmpty(data.get(15)))
                .build();
    }

    private String checkForNullAndEmpty(String stringToCheck){
        String value = stringToCheck != null && !stringToCheck.equals("") ? stringToCheck : null;
        return value;
    }
}

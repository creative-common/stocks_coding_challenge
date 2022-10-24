package com.rbc.stocksapi.controller;

import com.opencsv.exceptions.CsvException;
import com.rbc.stocksapi.exceptions.MyCustomException;
import com.rbc.stocksapi.models.DowJonesIndexDTO;
import com.rbc.stocksapi.services.CsvReaderService;
import com.rbc.stocksapi.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/stocks")
@Tag(name = "stocks", description = "DowJones Index api to add or retrieve data")
public class StocksController {

    private final StockService stockService;
    private final CsvReaderService csvReaderService;

    @Autowired
    public StocksController(StockService stockService,
                            CsvReaderService csvReaderService) {
        this.stockService = stockService;
        this.csvReaderService = csvReaderService;
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get stock info using ticker and quarter")
    public CompletableFuture<ResponseEntity<List<DowJonesIndexDTO>>>
    getByStockTickerAndQuarter(
            @Valid @RequestParam("ticker") String stockTicker,
                               @RequestParam("quarter") Integer quarter) {

        return stockService.getStocksByTickerAndQuarter(stockTicker, quarter)
                .thenApply(dowJonesIndexDTOList -> {
                    if (dowJonesIndexDTOList.isEmpty())
                        return ResponseEntity.notFound().build();
                    else
                        return ResponseEntity.ok().body(dowJonesIndexDTOList);
                });
    }

    @PostMapping(value = "/add")
    @Operation(summary = "Add a new ticker",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = DowJonesIndexDTO.class))))
    public CompletableFuture<ResponseEntity<DowJonesIndexDTO>> addStockTicker(
            @Valid @RequestBody DowJonesIndexDTO dowJonesIndexDTO
    ) {

        return stockService.addStockTicker(dowJonesIndexDTO)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    throw new MyCustomException(throwable); // Throw your exception according to the use case.
                });
    }

    @Operation(summary = "Upload list of tickers and data")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<String>> uploadStock(@Valid @RequestParam("file") MultipartFile file) throws IOException, CsvException {
        return csvReaderService.read(file)
                .thenAccept(stockService::addStockTickers)
                .thenApply(e -> ResponseEntity.ok("{ 'status': 'File uploaded successfully - '" + file.getOriginalFilename()+" }"))
                .exceptionally(throwable -> ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body("Error occured while uploading the file - " + file.getOriginalFilename() + "!"));

    }
}

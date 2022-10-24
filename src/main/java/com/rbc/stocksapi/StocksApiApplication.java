package com.rbc.stocksapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Dow Jones Stocks API", version = "1.0", description = "DowJones Index Information"))
public class StocksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApiApplication.class, args);
    }

}

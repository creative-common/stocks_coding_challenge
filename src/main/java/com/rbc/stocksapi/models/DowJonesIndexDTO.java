package com.rbc.stocksapi.models;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DowJonesIndexDTO {

    @NotNull
    @NotEmpty
    private String quarter;

    @NotNull
    @NotEmpty
    private String stock;

    @NotNull
    @NotEmpty
    private String date;

    private String open;

    private String high;

    private String low;

    private String close;

    private String volume;

    private String percent_change_price;

    private String percent_change_volume_over_last_wk;

    private String previous_weeks_volume;

    private String next_weeks_open;

    private String next_weeks_close;

    private String percent_change_next_weeks_price;

    private String days_to_next_dividend;

    private String percent_return_next_dividend;
}
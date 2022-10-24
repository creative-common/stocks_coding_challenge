# RBC Coding Challenge - Dow Jones Index

## Install and Run

```
mvn install && mvn spring-boot:run
```

## REST API
The following can be tested using Postman while the server is running.

### Swagger Docs
```
http://localhost:8080/swagger-ui/index.html
```
Swagger - [![https://i.ibb.co/n6dn3jT/swagger.png](https://i.ibb.co/n6dn3jT/swagger.png "https://i.ibb.co/n6dn3jT/swagger.png")](https://i.ibb.co/n6dn3jT/swagger.png "https://i.ibb.co/n6dn3jT/swagger.png")

### GET Dow Jones Indices by stock
```
http://localhost:8080/api/stocks/get?ticker=AA&quarter=1
```

[![https://prnt.sc/4YUBaMeOH9NF](https://i.ibb.co/bNkkHxq/image.png)](https://i.ibb.co/bNkkHxq/image.png)

### POST request to create a single new Stock Index
```
http://localhost:8080/api/stocks/add
```
Select a raw body with JSON type in Postman.

```
{
    "quarter": 2,
    "stock": "MSFT",
    "date": "2012/02/01",
    "open": 15.82,
    "high": 16.72,
    "low": 15.78,
    "close": 16.42,
    "volume": 239655616,
    "percent_change_price": 3.79267,
    "percent_change_volume_over_last_wk": null,
    "previous_weeks_volume": null,
    "next_weeks_open": 16.71,
    "next_weeks_close": 15.97,
    "percent_change_next_weeks_price": -4.42849,
    "days_to_next_dividend": 26,
    "percent_return_next_dividend": 0.182704
}

```
[![https://i.ibb.co/1GsVCZX/poststock.png](https://i.ibb.co/1GsVCZX/poststock.png "https://i.ibb.co/1GsVCZX/poststock.png")](https://i.ibb.co/1GsVCZX/poststock.png "https://i.ibb.co/1GsVCZX/poststock.png")

### Bulk post request - CSV Upload
```
http://localhost:8080/api/stocks/upload
```
Add a form-data body to the request with a key value of 'file', and a type of File and select csv file (from - )

(NOTE: Swagger http://localhost:8080/swagger-ui/index.html)

csv uploader - upload only non null / empty values of following columns
constraint - non null - stocks, quarter, date


### After uploading postman
[![https://ibb.co/PYr1zQP](https://i.ibb.co/kyGq61w/Screen-Shot-2022-10-24-at-11-45-34-AM.png "https://ibb.co/PYr1zQP")](https://i.ibb.co/kyGq61w/Screen-Shot-2022-10-24-at-11-45-34-AM.png "https://ibb.co/PYr1zQP")

### H2 database after uploading
[![https://ibb.co/JKdJn3M](https://i.ibb.co/9Nv5nZC/Screen-Shot-2022-10-24-at-11-46-21-AM.png "https://ibb.co/JKdJn3M")](https://i.ibb.co/9Nv5nZC/Screen-Shot-2022-10-24-at-11-46-21-AM.png "https://ibb.co/JKdJn3M")






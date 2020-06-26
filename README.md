#economic-statistic-service

Реализованные запросы:

GET: /api/economic-sectors 


GET: /api/products/{economicCode}


GET: /api/products 

POST /api/sector-statistic 


Test Request body:


{ 
   "startDate" : "2019-09-01",
   "endDate" : "2019-10-01",
   "economicCode" : "U",
   "currencyCode" : "RUB"
}

POST: /api/compare-economic-sector

   { 
   "startFromPeriod" : "2019-04-01",
   "endFromPeriod" : "2019-05-01",
   "startToPeriod" : "2020-04-01",
   "endToPeriod" : "2020-05-01",
   "economicCode" : "M",
   "currencyCode" : "RUB"
   }

Можно посмотреть запросы в Swagger:

http://localhost:8080/swagger-ui-custom.html


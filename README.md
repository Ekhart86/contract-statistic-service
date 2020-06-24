# economic-statistic-service

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

Можно посмотреть запросы в Swagger:

http://localhost:8080/swagger-ui-custom.html


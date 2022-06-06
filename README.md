# Lease API

The **Lease API** is REST API which allows to maintain lease for vehicle and to check lease rate.

### Run API locally

#### Build application
```mvn clean install```

#### Run application
```mvn spring-boot:run```

Note: the application starts on port 8084.

#### Test API

##### GET Lease Rate
In order to test leaserate endpoint, first create car in H2 database by using POST endpoint of Car API,

###### Create Car
```POST http://localhost:8081/api/car```
Request body example:
```
{
"make": "Ford",
"model": "Ford Fiesta",
"version": "1",
"numberOfDoors": 4,
"co2Emission": 3.8,
"grossPrice": 34000.8,
"nettPrice": 30000.9,
"mileage": 1000
}
```
Get car id which is returned in the response and send this id to the lease rate endpoint

###### Get Lease Rate
```GET http://localhost:8084/api/lease/leaserate```
Request body example:
```
{
    "carId": 1,
    "contractDuration": 10
}
```

##### GET (Read lease details)
```GET http://localhost:8084/api/lease/<id>```

##### POST (Create new lease)
```POST http://localhost:8084/api/lease```
Request body example:
```
{
    "customerId": 12,
    "carId": 23,
    "startDate": "12-02-2021",
    "endDate": "02-05-2021",
    "interestRate": 3.2
}
```

##### PUT (Update lease details)
```PUT http://localhost:8084/api/lease/<id>```
Request body example:
```
{
    "customerId": 1,
    "carId": 6,
    "startDate": "19-02-2022",
    "endDate": "02-05-2022",
    "interestRate": 7
}
```

##### DELETE (Delete lease)
```DELETE http://localhost:8084/api/lease/<id>```


# spring boot graphql rest based implementation

### To compile 
* mvn clean package -DskipTests

# To fetch employee by id dynamic 
- localhost:8080/employee
- post 
```
{ 
    "queryName":"getEmployee",
    "query": "{ getEmployee{ id firstName lastName doj gender attributes phone salary career} }",
    "param": {"id":"1"}
}
```
* response 
````
{
    "data": {
        "id": 1,
        "firstName": "adarsh",
        "lastName": "kumar",
        "career": "It",
        "salary": 10,
        "doj": "2020-01-01",
        "gender": "MALE",
        "attributes": {
            "key1": "value"
        },
        "phone": [
            "1234567890",
            "1234567890"
        ]
    },
    "errors": []
}
````
## To fetch all employee dynamically based on properties 
- localhost:8080/employee
- post request 
```
{ 
    "queryName":"getAllEmployee",
    "query": "{ getAllEmployee{ id firstName lastName doj gender attributes phone salary career} }"
}
```

* response
```
{
    "data": [
        {
            "id": 1,
            "firstName": "adarsh",
            "lastName": "kumar",
            "career": "It",
            "salary": 10,
            "doj": "2020-01-01",
            "gender": "MALE",
            "attributes": {
                "key1": "value"
            },
            "phone": [
                "1234567890",
                "1234567890"
            ]
        },
        {
            "id": 2,
            "firstName": "radha",
            "lastName": "singh",
            "career": "IT",
            "salary": 10,
            "doj": "2020-01-01",
            "gender": "FEMALE",
            "attributes": {
                "key2": "value"
            },
            "phone": [
                "1234567890",
                "1234567890"
            ]
        },
        {
            "id": 3,
            "firstName": "sonu",
            "lastName": "singh",
            "career": "IT",
            "salary": 5,
            "doj": "2020-01-01",
            "gender": "MALE",
            "attributes": {
                "key3": "value"
            },
            "phone": [
                "1234567890",
                "1234567890"
            ]
        },
        {
            "id": 4,
            "firstName": "amit",
            "lastName": "kumar",
            "career": "Finance",
            "salary": 8,
            "doj": "2020-01-01",
            "gender": "MALE",
            "attributes": {
                "key4": "value"
            },
            "phone": [
                "1234567890",
                "1234567890"
            ]
        }
    ],
    "errors": []
}
```

### To fetch employee based on filter condition 
- localhost:8080/employee
- post request
```
{ 
    "queryName":"employeesFilter",
    "query": "{ employeesFilter{ id firstName lastName doj gender} }",
    "param": {"filter":{"id":null,"salary": { "operator" : "gt" ,"value": "5" },"carrier":null }}
}
```

* response
```
{
    "data": [
        {
            "id": 1,
            "firstName": "adarsh",
            "lastName": "kumar",
            "doj": "2020-01-01",
            "gender": "MALE"
        },
        {
            "id": 2,
            "firstName": "radha",
            "lastName": "singh",
            "doj": "2020-01-01",
            "gender": "FEMALE"
        },
        {
            "id": 4,
            "firstName": "amit",
            "lastName": "kumar",
            "doj": "2020-01-01",
            "gender": "MALE"
        }
    ],
    "errors": []
}
```


### To create employee based 
- localhost:8080/employee
- post request
```
{ 
    "queryName":"saveEmployee",
    "query": "{ saveEmployee(employeeBean: {id: 10, firstName: \"sonu\", lastName: \"kumar\", career: \"it\", salary: 3, doj: \"2020-01-01\", gender: MALE, phone: null, attributes: null} ){ id firstName lastName doj gender} }",
    "param": {"employeeBean":{ "id":10,"firstName":"sonu","lastName":"kumar","career":"it", "salary": 3,  "doj": "2020-01-01", "gender": "MALE"}}
}
```
* response 
```
{
    "data": {
        "id": 10,
        "firstName": "sonu",
        "lastName": "kumar",
        "doj": "2020-01-01",
        "gender": "MALE"
    },
    "errors": []
}
```

### To update employee based
- localhost:8080/employee
- post request
```
{ 
    "queryName":"updateEmployee",
    "query": "{ updateEmployee(employeeBean: {id: 10, firstName: \"sonu\", lastName: \"kumar\", career: \"it\", salary: 3, doj: \"2020-01-01\", gender: MALE, phone: null, attributes: null} ){ id firstName lastName doj gender} }",
    "param": {"employeeBean": { "id":10,"firstName":"sonu","lastName":"kumar singh","career":"it", "salary": 3,  "doj": "2020-01-01", "gender": "MALE"}}
}
```
* response
```
{
    "data": {
        "id": 10,
        "firstName": "sonu",
        "lastName": "kumar singh",
        "doj": "2020-01-01",
        "gender": "MALE"
    },
    "errors": []
}
```


### To update Partial  employee based
- localhost:8080/employee
- post request
```
{ 
    "queryName":"updatePartialEmployee",
    "query": "{ updatePartialEmployee(employeeBean: {id: 10, firstName: \"sonu\", lastName: \"kumar\", career: \"it\", salary: 3, doj: \"2020-01-01\", gender: MALE, phone: null, attributes: null} ){ id firstName lastName doj gender} }",
    "param": {"employeeBean": { "id":10,"lastName":"kumar singh thakur"}}
}
```
* response
```
{
    "data": {
        "id": 10,
        "firstName": "sonu",
        "lastName": "kumar singh thakur",
        "doj": "2020-01-01",
        "gender": "MALE"
    },
    "errors": []
}
```

### To remove employee based
- localhost:8080/employee
- post request
```
{ 
    "queryName":"removeEmployee",
    "query": "{ removeEmployee{ id firstName lastName doj gender} }",
    "param": {"id": 1}
}
```
* response
```
{
    "data": {
        "id": 1,
        "firstName": "adarsh",
        "lastName": "kumar",
        "doj": "2020-01-01",
        "gender": "MALE"
    },
    "errors": []
}
```



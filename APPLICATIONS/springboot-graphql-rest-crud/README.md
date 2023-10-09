# spring boot graphql rest based implementation 

*  https://www.graphql-java.com/documentation/v21/getting-started
* - https://nulpointerexception.com/2020/04/20/introduction-to-graphql-mutation-through-java/


### To compile 
* mvn clean package -DskipTests

- To fetch all employee 
    - http://localhost:8080/employees
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
                    "id": 3,
                    "firstName": "sonu",
                    "lastName": "singh",
                    "doj": "2020-01-01",
                    "gender": "MALE"
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


# To fetch employee by id dynamic 
- localhost:8080/employee/fetch
- post 
```
{ 
    "queryName":"getEmployee",
    "query": "getEmployee{ id firstName lastName doj gender attributes phone salary career}",
    "id":"1"
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


- To fetch employee by id 
    - http://localhost:8080/employee/1
```   
{
  "data": {
    "id": 1,
    "firstName": "adarsh",
    "lastName": "kumar",
    "doj": "2020-01-01",
    "gender": "MALE"
  },
  "errors": [    
  ]
}
```
  - http://localhost:8080/employee/100
```
{
  "errors": [
    "employee not found"
  ]
}
```

- To fetch Filter employee 
    - http://localhost:8080/employee/filter
    - post call
    - request payload 
          ```
          {"id":null,"salary": { "operator" : "gt" ,"value": "5" },"carrier":null}
          ``` 
      - response 
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
        
  - To save data 
    - http://localhost:8080/employee
    - post call 
    - request body 
      ```
       { "id":10,"firstName":"sonu","lastName":"kumar","career":"it", "salary": 3,  "doj": "2020-01-01", "gender": "MALE"}
      ```
      - response body 
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

    - To update data
        - http://localhost:8080/employee
        - put call
        - request body
    ```
           { "id":10,"firstName":"sonu","lastName":"kumar singh","career":"it", "salary": 3,  "doj": "2020-01-01", "gender": "MALE"}
    ```
          - response body
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
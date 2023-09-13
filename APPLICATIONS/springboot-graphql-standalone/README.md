# spring boot graphql standalone implementation 

*  https://www.graphql-java.com/documentation/v21/getting-started
* - https://nulpointerexception.com/2020/04/20/introduction-to-graphql-mutation-through-java/

- To fetch all employee 
    - http://localhost:8080/employees
```   
[
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
]
```

- To fetch employee by id 
    - http://localhost:8080/employee/1
```   
{
  "id": 1,
  "firstName": "adarsh",
  "lastName": "kumar",
  "doj": "2020-01-01",
  "gender": "MALE"
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
      [
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
       ]
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
    "id": 10,
    "firstName": "sonu",
    "lastName": "kumar",
    "doj": "2020-01-01",
    "gender": "MALE"
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
          "id": 10,
          "firstName": "sonu",
          "lastName": "kumar singh ",
          "doj": "2020-01-01",
          "gender": "MALE"
           }
        ```
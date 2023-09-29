# SPRING BOOT-BASIC-CRUD-EXAMPLE

---

### to build the application
* $ mvn clean package

### To Run the application
* $ mvn spring-boot:run

### log into the homepage of db
* http://localhost:8080/h2-console
```
username, pwd , dburl and db driver class is mentioned in application.properties file
```
* voyager console
* http://localhost:8080/espark/voyager


* playgroup console
* http://localhost:8080/espark/playground
* url for graphiql console 
* http://localhost:8080/espark/graphiql

* query
### To get the Employee by Id  
```
{
  getEmployee(id:1 ){
  id
  firstName
  lastName
  salary
  }
}
```
* response 
```
{
  "data": {
    "getEmployee": {
      "id": "1",
      "firstName": "adarsh",
      "lastName": "kumar",
      "salary": 10
    }
  }
}
```

### To get the All Employee
```
{
  getAllEmployee{
    id
    firstName
    lastName
    salary
    career
    attributes
    
  }
}
```
* response 
```
{
  "data": {
    "getAllEmployee": [
      {
        "id": "1",
        "firstName": "adarsh",
        "lastName": "kumar",
        "salary": 10,
        "career": "It",
        "attributes": "{\"key1\":\"value\"}"
      },
      {
        "id": "2",
        "firstName": "radha",
        "lastName": "singh",
        "salary": 10,
        "career": "IT",
        "attributes": "{\"key2\":\"value\"}"
      },
      {
        "id": "3",
        "firstName": "sonu",
        "lastName": "singh",
        "salary": 5,
        "career": "IT",
        "attributes": "{\"key3\":\"value\"}"
      },
      {
        "id": "4",
        "firstName": "amit",
        "lastName": "kumar",
        "salary": 8,
        "career": "Finance",
        "attributes": "{\"key4\":\"value\"}"
      }
    ]
  }
}
```
* Mutations 

### saving data 
```
mutation{
  saveEmployee(employeeBean:{ id:10,firstName:"sonu",lastName:"singh",career:"it", salary: 3, doj:"1010-01-01", gender:MALE, attributes:"{\"key\":\"value\"}", phone:["1234567890","12345678999"]}) {
    id firstName lastName career salary doj gender phone
  }
}
```
* response 
```
{
  "data": {
    "saveEmployee": {
      "id": "10",
      "firstName": "sonu",
      "lastName": "singh",
      "career": "it",
      "salary": 3,
      "doj": "1010-01-01",
      "gender": "MALE",
      "phone": [
        "1234567890",
        "12345678999"
      ]
    }
  }
}
```

### updating data 
```
mutation{
  updateEmployee(employeeBean:{ id:10,firstName:"sonu",lastName:"kumar singh",career:"it", salary: 3}) {
    id firstName lastName career salary
  }
}
```
* response 
```
{
  "data": {
    "updateEmployee": {
      "id": "10",
      "firstName": "sonu",
      "lastName": "kumar singh",
      "career": "it",
      "salary": 3
    }
  }
}
```


### removing data
```
mutation{
  removeEmployee(id: 10){
    id
    firstName
    lastName
    career
  }
}
```
* response 
```
{
  "data": {
    "removeEmployee": {
      "id": "10",
      "firstName": "sonu",
      "lastName": "kumar singh",
      "career": "it"
    }
  }
}
```


---
# Curl Cmd Operation 

---

### AllEmployee
```
curl --location 'http://localhost:8080/api/espark/graphql' \
--header 'Content-Type: application/json' \
--header 'Cookie: cookieName=' \
--data '{"query":"query{getAllEmployee{  id  firstName  lastName  salary}\n}"}'
```

### Get Employee By Id
```
curl --location 'http://localhost:8080/api/espark/graphql' \
--header 'Content-Type: application/json' \
--header 'Cookie: cookieName=' \
--data '{
  "query": "query{getEmployee(id:1){  id  firstName  lastName  salary}\n}"
}'
```

### Saving Employee 
```
curl --location 'http://localhost:8080/api/espark/graphql' \
--header 'Content-Type: application/json' \
--header 'Cookie: cookieName=' \
--data '{"query":"mutation{saveEmployee(employeeBean:{ id:10,firstName:\"sonu\",lastName:\"singh\",career:\"it\", salary: 3}) {  id firstName lastName career salary}\n}"}'
```


### Updating Employee 
```
curl --location 'http://localhost:8080/api/espark/graphql' \
--header 'Content-Type: application/json' \
--header 'Cookie: cookieName=' \
--data '{"query":"mutation{updateEmployee(employeeBean:{ id:10,firstName:\"sonu\",lastName:\"kumar singh\",career:\"it\", salary: 3}) {  id firstName lastName career salary}\n}"}'

```

### Deleting Employee
```
curl --location 'http://localhost:8080/api/espark/graphql' \
--header 'Content-Type: application/json' \
--data '{
	"query": "mutation{removeEmployee(id: 10){  id  firstName  lastName  career}\n}"
}'
```



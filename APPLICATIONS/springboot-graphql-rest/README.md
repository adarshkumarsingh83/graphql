# SPRING BOOT GRAPHQL REST BASE



- to Fetch the string content 
  * http://localhost:8080/message/adarsh
```
{message=welcome to the espark adarsh}
```

- to fetch json map key value pair 
    * http://localhost:8080/data/adarsh
```
{
  "data": "{\"date\":\"2023-09-12\",\"name\":\"adarsh\",\"message\":\"welcome to the espark adarsh\"}"
}
```

- To fetch custom bean object in json 
  * http://localhost:8080/employee/100
```
{
  "id": 100,
  "name": "adarsh kumar",
  "dob": "2023-09-12",
  "gender": "MALE"
}
```

- To fetch custom bean object list 
    * http://localhost:8080/employees
```
[
  {
    "id": 100,
    "name": "adarsh kumar",
    "dob": "2023-09-12",
    "gender": "MALE"
  },
  {
    "id": 101,
    "name": "raddh singh",
    "dob": "2023-09-12",
    "gender": "FEMALE"
  }
]
```
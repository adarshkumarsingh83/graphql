# springboot-graphql-rest-query-engine


### to build the application
* $ mvn clean package

### To run 
* $ mvn spring-boot:run

### log into the homepage of db
* http://localhost:8080/h2-console
    * username, pwd , dburl and db driver class is mentioned in application.yml file

# To Fetch Data
* $ curl -v localhost:8080/employees


### get all employee
```
curl --location 'http://localhost:8080/graphql/employees' \
--header 'Content-Type: application/json' \
--data '{
    "query":"{getAllEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } projects   {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  } }"
    ,"queryName":"getAllEmployee"
}'
```
* response
```
{
    "data": [
        {
            "employeeId": 10,
            "firstName": "adarsh",
            "lastName": "kumar",
            "career": "It",
            "contact": {
                "contactId": 10,
                "type": "primary",
                "email": "adarsh@kumar.com",
                "phone": [
                    "1234567890",
                    "1234567890"
                ]
            },
            "address": {
                "addressId": 10,
                "street": "delhi street",
                "state": "delhi",
                "country": "in"
            },
            "department": {
                "deptId": 10,
                "name": "it",
                "address": {
                    "addressId": 10,
                    "street": "delhi street",
                    "state": "delhi",
                    "country": "in"
                },
                "contact": {
                    "contactId": 10,
                    "type": "primary",
                    "email": "adarsh@kumar.com",
                    "phone": [
                        "1234567890",
                        "1234567890"
                    ]
                }
            },
            "projects": [
                {
                    "projectId": 10,
                    "name": "adarsh-project1",
                    "startDate": null,
                    "endDate": null,
                    "address": {
                        "addressId": 10,
                        "street": "delhi street",
                        "state": "delhi",
                        "country": "in"
                    },
                    "contact": {
                        "contactId": 10,
                        "type": "primary",
                        "email": "adarsh@kumar.com",
                        "phone": [
                            "1234567890",
                            "1234567890"
                        ]
                    }
                }
            ]
        },
        {
            "employeeId": 20,
            "firstName": "radha",
            "lastName": "singh",
            "career": "IT",
            "contact": {
                "contactId": 20,
                "type": "primary",
                "email": "radha@singh.com",
                "phone": [
                    "1234567890",
                    "1234567890"
                ]
            },
            "address": {
                "addressId": 20,
                "street": "hyd street",
                "state": "ap",
                "country": "in"
            },
            "department": {
                "deptId": 20,
                "name": "management",
                "address": {
                    "addressId": 20,
                    "street": "hyd street",
                    "state": "ap",
                    "country": "in"
                },
                "contact": {
                    "contactId": 20,
                    "type": "primary",
                    "email": "radha@singh.com",
                    "phone": [
                        "1234567890",
                        "1234567890"
                    ]
                }
            },
            "projects": [
                {
                    "projectId": 20,
                    "name": "adarsh-project2",
                    "startDate": null,
                    "endDate": null,
                    "address": {
                        "addressId": 20,
                        "street": "hyd street",
                        "state": "ap",
                        "country": "in"
                    },
                    "contact": {
                        "contactId": 20,
                        "type": "primary",
                        "email": "radha@singh.com",
                        "phone": [
                            "1234567890",
                            "1234567890"
                        ]
                    }
                }
            ]
        },
        {
            "employeeId": 30,
            "firstName": "sonu",
            "lastName": "singh",
            "career": "IT",
            "contact": {
                "contactId": 30,
                "type": "primary",
                "email": "sounu@singh.com",
                "phone": [
                    "1234567890",
                    "1234567890"
                ]
            },
            "address": {
                "addressId": 30,
                "street": "ald street",
                "state": "up",
                "country": "in"
            },
            "department": {
                "deptId": 30,
                "name": "operations",
                "address": {
                    "addressId": 30,
                    "street": "ald street",
                    "state": "up",
                    "country": "in"
                },
                "contact": {
                    "contactId": 30,
                    "type": "primary",
                    "email": "sounu@singh.com",
                    "phone": [
                        "1234567890",
                        "1234567890"
                    ]
                }
            },
            "projects": [
                {
                    "projectId": 30,
                    "name": "radha-project1",
                    "startDate": null,
                    "endDate": null,
                    "address": {
                        "addressId": 30,
                        "street": "ald street",
                        "state": "up",
                        "country": "in"
                    },
                    "contact": {
                        "contactId": 30,
                        "type": "primary",
                        "email": "sounu@singh.com",
                        "phone": [
                            "1234567890",
                            "1234567890"
                        ]
                    }
                }
            ]
        },
        {
            "employeeId": 40,
            "firstName": "amit",
            "lastName": "kumar",
            "career": "Finance",
            "contact": {
                "contactId": 40,
                "type": "primary",
                "email": "amit@kumar.com",
                "phone": [
                    "1234567890",
                    "1234567890"
                ]
            },
            "address": {
                "addressId": 40,
                "street": "delhi street",
                "state": "delhi",
                "country": "in"
            },
            "department": {
                "deptId": 40,
                "name": "finance",
                "address": {
                    "addressId": 40,
                    "street": "delhi street",
                    "state": "delhi",
                    "country": "in"
                },
                "contact": {
                    "contactId": 40,
                    "type": "primary",
                    "email": "amit@kumar.com",
                    "phone": [
                        "1234567890",
                        "1234567890"
                    ]
                }
            },
            "projects": [
                {
                    "projectId": 40,
                    "name": "radha-project2",
                    "startDate": null,
                    "endDate": null,
                    "address": {
                        "addressId": 40,
                        "street": "delhi street",
                        "state": "delhi",
                        "country": "in"
                    },
                    "contact": {
                        "contactId": 40,
                        "type": "primary",
                        "email": "amit@kumar.com",
                        "phone": [
                            "1234567890",
                            "1234567890"
                        ]
                    }
                }
            ]
        }
    ],
    "errors": []
}
```

### get Employee By id 
```
curl --location 'http://localhost:8080/graphql/employee/10' \
--header 'Content-Type: application/json' \
--data '{
    "query":"{getEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } projects   {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  } }"
    ,"queryName":"getEmployee"
}'
```
* response 
```
{
    "data": {
        "employeeId": 10,
        "firstName": "adarsh",
        "lastName": "kumar",
        "career": "It",
        "contact": {
            "contactId": 10,
            "type": "primary",
            "email": "adarsh@kumar.com",
            "phone": [
                "1234567890",
                "1234567890"
            ]
        },
        "address": {
            "addressId": 10,
            "street": "delhi street",
            "state": "delhi",
            "country": "in"
        },
        "department": {
            "deptId": 10,
            "name": "it",
            "address": {
                "addressId": 10,
                "street": "delhi street",
                "state": "delhi",
                "country": "in"
            },
            "contact": {
                "contactId": 10,
                "type": "primary",
                "email": "adarsh@kumar.com",
                "phone": [
                    "1234567890",
                    "1234567890"
                ]
            }
        },
        "projects": [
            {
                "projectId": 10,
                "name": "adarsh-project1",
                "startDate": null,
                "endDate": null,
                "address": {
                    "addressId": 10,
                    "street": "delhi street",
                    "state": "delhi",
                    "country": "in"
                },
                "contact": {
                    "contactId": 10,
                    "type": "primary",
                    "email": "adarsh@kumar.com",
                    "phone": [
                        "1234567890",
                        "1234567890"
                    ]
                }
            }
        ]
    },
    "errors": []
}
```




### regex query

* without any regex complete query
```
{
    "query":"{getAllEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } projects   {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  } }"
    ,"queryName":"getAllEmployee"
}
```

* with regex for all employees employee{*}
```
{
    "query":"{getAllEmployee employees{*} }" 
    ,"queryName":"getAllEmployee"
}
```

* with regex for all employees department{*}
````
{
    "query":"{ getAllEmployee  {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   department{*} projects   {  projectId  name  startDate  endDate  address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  }  }" 
    ,"queryName":"getAllEmployee"
}
````

* with regex for all employees department{*} and  projects{*}
````
{
    "query":"{ getAllEmployee  {  employeeId  firstName  lastName  career contact   contact{*} address   {  addressId  street  state  country  } department   department{*} projects  projects{*} }  }" 
    ,"queryName":"getAllEmployee"
}
````

* with regex for all employees department{*} and  projects{*} and contact{*}

````
{
    "query":"{ getAllEmployee  {  employeeId  firstName  lastName  career contact   contact{*} address   address{*}  department   department{*} projects  projects{*} }  }" 
    ,"queryName":"getAllEmployee"
}
````

* with regex for all employees -> project  contact{*} and address{*}
````

{
    "query":"{ getAllEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } projects   {  projectId  name  startDate  endDate  contact contact{*}  address  address{*}   }  } }"
    ,"queryName":"getAllEmployee"
}

````

* with regex for all employees -> department  contact{*} and address{*}
````
{
    "query":"{getAllEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name contact contact{*}  address  address{*} } projects   {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  } }"
    ,"queryName":"getAllEmployee"
}
````

* with regex for all employees -> department and project  contact{*} and address{*}
```
{
    "query":"{ getAllEmployee {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name contact contact{*}  address  address{*} } projects   {  projectId  name  startDate  endDate contact contact{*}  address  address{*}  }  } }"
    ,"queryName":"getAllEmployee"
}

```

### regex query generation in logs

```
 regex employees{*} query  {  employeeId  firstName  lastName  career contact   {  contactId  type  email  phone  } address   {  addressId  street  state  country  } department   {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } projects   {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  }  } 


 regex department{*} query  {  deptId  name address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } 

 regex address{*} query  {  addressId  street  state  country  } 

 regex contact{*} query  {  contactId  type  email  phone  } 

 regex projects{*} query  {  projectId  name  startDate  endDate address   {  addressId  street  state  country  } contact   {  contactId  type  email  phone  }  } 

```


# SPRING BOOT BASIC EXAMPLE WITH GRAPHQL 

## To pass the value using graphql context 
```
    @QueryMapping
    public List<AcademicClass> schedule(@Argument Teacher teacher,
                                        GraphQLContext graphQLContext) {
        graphQLContext.put("key1","value1");
         ..............
        }
        
      @BatchMapping
    public Callable<Map<AcademicClass, Difficulty>> difficulty(List<AcademicClass> academicClasses,
                                                               @ContextValue String key1,
                                                               GraphQLContext context) {
        log.info("difficulty() executed ");
        log.info(key1);
        String value = context.get("key1");
        log.info(value);
        
        ..............
        
        
     }   

```

- graphql ui url 
- http://localhost:8080/espark/graphiql?path=/api/espark/graphql
- query 
```
query{
  schedule(teacher: TEACHER1){
    id
    teacher
    startAt
    endsAt
    difficulty
  }
}
```
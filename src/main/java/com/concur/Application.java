package com.concur;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@SpringBootApplication
public class Application {

public static void main(String[] args){

    //Creating an Object that will be returned.
    Person person = new Person("Chandra", "Nadukula", "2480 Glengyle Drive Vienna VA 22180");

    String schema = "type Query{firstName: String lastName: String address: String} schema{query: Query}";

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    RuntimeWiring runtimeWiring = newRuntimeWiring()
                                    .type("Query", builder -> builder.dataFetcher("firstName", new StaticDataFetcher(person.getFirstName())))
                                    .type("Query", builder -> builder.dataFetcher("lastName", new StaticDataFetcher(person.getLastName())))
                                    .type("Query", builder -> builder.dataFetcher("address", new StaticDataFetcher(person.getAddress())))
                                    .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

    GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
    ExecutionResult executionResult = build.execute("{firstName lastName address}");

    System.out.println(executionResult.getData().toString());

    //SpringApplication.run(Application.class, args);
}

}

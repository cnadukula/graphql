package com.concur;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person {

    public Person(){}

    public Person(String firstName, String lastName, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    private String firstName;
    private String lastName;
    private String address;
}

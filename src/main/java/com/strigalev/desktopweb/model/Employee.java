package com.strigalev.desktopweb.model;

import lombok.Data;


@Data
public class Employee {
    private static long instancesCounter = 0;


    private long id;
    private String firstName;
    private String lastName;
    private String emailId;

    public Employee() {
        instancesCounter++;
    }

    public Employee(long id,
                    String firstName,
                    String lastName,
                    String emailId) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        instancesCounter++;
    }

    public static long count() {
        return instancesCounter;
    }

}

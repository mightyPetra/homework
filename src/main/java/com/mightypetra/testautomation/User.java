package com.mightypetra.testautomation;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class User {

    private static final String defaultName = "name";
    private static final String defaultSurname = "surname";

    private String name;
    private String surname;
    private String email;
    private String password;
    private String birthDate;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        String emailTemplate = "%s.%s@mail.com";
        this.email = String.format(emailTemplate, name, surname);
        this.password = randomUUID().toString();
        this.birthDate = "13/01/1999";
    }

    public User() {
        this.name = generateString();
        this.surname = generateString();
        String emailTemplate = "%s.%s@mail.com";
        this.email = String.format(emailTemplate, name, surname);
        this.password = randomUUID().toString();
        this.birthDate = "13/01/1999";
    }

    public String generateString() {
        return UUID.randomUUID().toString().replaceAll("[\\d-]","");
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return name+" "+surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }
}

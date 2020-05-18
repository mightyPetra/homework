package com.mightypetra.testautomation;

import static java.util.UUID.randomUUID;


/**
 * This should be passed from the config file. TODO
 **/

public class UserProfile {

    private static final String defaultName = "name";
    private static final String defaultSurname = "surname";

    private String name;
    private String surname;
    private String email;
    private String password;
    private String birthDate;
    private String address;
    private String zip;
    private String city;
    private String country;

    public UserProfile(String name, String surname) {
        this.name = name;
        this.surname = surname;
        String emailTemplate = "%s.%s@mail.com";
        this.email = String.format(emailTemplate, name, surname);
        this.password = randomUUID().toString();
        this.birthDate = "13/01/1999";
        this.address = "Hyrule street 3";
        this.zip = "66666";
        this.city = "Kakariko Village";
    }

    public UserProfile() {
        this(Utils.generateString(), Utils.generateString());
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

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

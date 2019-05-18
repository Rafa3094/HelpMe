package com.helpme.Entities;


import java.util.Date;

public class User {

    private Integer id;
    private String name;
    private String lastName;
    private Date age;
    private String bloodType;

    public User(Integer id, String name, String lastName, Date age, String bloodType) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.bloodType = bloodType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}

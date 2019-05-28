package com.helpme.Entities;

public class EmergencyNumber {

    private String name;
    private String phone;

    public EmergencyNumber() {
    }

    public EmergencyNumber(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {

        return name + " \t\t " + phone;

    }
}

package com.helpme.Entities;

public class EmergencyNumber {

    private String name;
    private int phone;

    public EmergencyNumber() {
    }

    public EmergencyNumber(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {

        return name + " \t\t " + phone;

    }
}

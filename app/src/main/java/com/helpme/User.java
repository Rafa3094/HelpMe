package com.helpme;

public class User {

    private int id;
    private int personalId;
    private String name;
    private String lastName;
    private String bitrhDate;
    private String sufferings;
    private String blood;

    public User(int id, int personalId, String name, String lastName, String bitrhDate, String sufferings, String blood) {
        this.id = id;
        this.personalId = personalId;
        this.name = name;
        this.lastName = lastName;
        this.bitrhDate = bitrhDate;
        this.sufferings = sufferings;
        this.blood = blood;
    }

    public User(int personalId, String name, String lastName, String bitrhDate, String sufferings, String blood) {
        this.personalId = personalId;
        this.name = name;
        this.lastName = lastName;
        this.bitrhDate = bitrhDate;
        this.sufferings = sufferings;
        this.blood = blood;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
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

    public String getBitrhDate() {
        return bitrhDate;
    }

    public void setBitrhDate(String bitrhDate) {
        this.bitrhDate = bitrhDate;
    }

    public String getSufferings() {
        return sufferings;
    }

    public void setSufferings(String sufferings) {
        this.sufferings = sufferings;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}

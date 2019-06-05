package com.helpme;

public class User {

    private int id;
    private String personalId;
    private String name;
    private String lastName;
    private String bitrhDate;
    private String sufferings;
    private String blood;

    public User(int id, String personalId, String name, String lastName, String bitrhDate, String sufferings, String blood) {
        this.id = id;
        this.personalId = personalId;
        this.name = name;
        this.lastName = lastName;
        this.bitrhDate = bitrhDate;
        this.sufferings = sufferings;
        this.blood = blood;
    }

    public User(String personalId, String name, String lastName, String bitrhDate, String sufferings, String blood) {
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

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", personalId=" + personalId +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bitrhDate='" + bitrhDate + '\'' +
                ", sufferings='" + sufferings + '\'' +
                ", blood='" + blood + '\'' +
                '}';
    }
}

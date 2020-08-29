package com.hvtechnologies.womansecurityapp;

public class ContactsEmergencyClass {


    String Name ;
    String PhNumber ;


    public ContactsEmergencyClass(String name, String phNumber) {
        Name = name;
        PhNumber = phNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhNumber() {
        return PhNumber;
    }

    public void setPhNumber(String phNumber) {
        PhNumber = phNumber;
    }
}

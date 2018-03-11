package com.androidfizz.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Aklesh on 9/3/2018.
 */

@Entity
public class ModelPerson {
    @PrimaryKey(autoGenerate = true)
    private int personID;
    private String name, email, age;
    @Embedded
    public Address address;

    @Ignore
    public int extraField;

    public ModelPerson(String name, String email, String age,Address address) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getExtraField() {
        return extraField;
    }

    public void setExtraField(int extraField) {
        this.extraField = extraField;
    }
}

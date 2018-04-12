package com.elad.kstream.childdemo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by eladw on 4/11/18.
 */
public class Child implements Identity<String>{

    private String id;
    private int age;
    private String firstName;
    private String lastName;
    private double hight;

    public Child() {
    }

    public Child(String id, int age, String firstName, String lastName, double hight) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hight = hight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getHight() {
        return hight;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Child{");
        sb.append("id=").append(id);
        sb.append(", age=").append(age);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", hight=").append(hight);
        sb.append('}');
        return sb.toString();
    }

    @JsonIgnore
    public String getKey() {
        return this.id;
    }
}

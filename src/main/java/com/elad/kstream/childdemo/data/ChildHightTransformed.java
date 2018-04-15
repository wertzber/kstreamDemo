package com.elad.kstream.childdemo.data;

/**
 * Created by eladw on 4/15/18.
 */
public class ChildHightTransformed {

    private String id;
    private int age;
    private double hight;

    public ChildHightTransformed() {
    }

    public ChildHightTransformed(String id, int age, double hight) {
        this.id = id;
        this.age = age;
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

    public double getHight() {
        return hight;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChildHightTransformed{");
        sb.append("id='").append(id).append('\'');
        sb.append(", age=").append(age);
        sb.append(", hight=").append(hight);
        sb.append('}');
        return sb.toString();
    }
}

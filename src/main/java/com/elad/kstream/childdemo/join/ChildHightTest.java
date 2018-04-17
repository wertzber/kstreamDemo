package com.elad.kstream.childdemo.join;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.Identity;
import com.elad.kstream.childdemo.data.TestResult;

/**
 * Created by eladw on 4/15/18.
 */
public class ChildHightTest implements Identity<String>{

    private String id;
    private int age;
    private double hight;
    private String category;
    private int grade;

    public ChildHightTest() {
    }

    public ChildHightTest(String id, int age, double hight, String category, int grade) {
        this.id = id;
        this.age = age;
        this.hight = hight;
        this.category = category;
        this.grade = grade;
    }

    public static ChildHightTest createHightTestFromChildAndTest (Child child, TestResult testResultsult) {
        return new ChildHightTest(child.getId(), child.getAge(), child.getHight(), testResultsult.getCategory(), testResultsult.getGrade());
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChildAndTestResultJoiner{");
        sb.append("id='").append(id).append('\'');
        sb.append(", age=").append(age);
        sb.append(", hight=").append(hight);
        sb.append(", category='").append(category).append('\'');
        sb.append(", grade=").append(grade);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public String getKey() {
        return this.id;
    }
}

package com.elad.kstream.childdemo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by eladw on 4/11/18.
 */
public class TestResult implements Identity<String>{

    @JsonProperty("id")
    private  String childId;
    private String category;
    private int grade;
    private long time;

    public TestResult() {
    }

    public TestResult(String category, int grade, String childId, int time) {
        this.category = category;
        this.grade = grade;
        this.childId = childId;
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestResult{");
        sb.append("childId='").append(childId).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", grade=").append(grade);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
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

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @JsonIgnore
    @Override
    public String getKey() {
        return this.childId;
    }
}

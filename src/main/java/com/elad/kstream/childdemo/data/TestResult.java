package com.elad.kstream.childdemo.data;

/**
 * Created by eladw on 4/11/18.
 */
public class TestResult {

    public String category;
    public int grade;
    public int childId;

    public TestResult(String category, int grade, int childId) {
        this.category = category;
        this.grade = grade;
        this.childId = childId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestResult{");
        sb.append("category='").append(category).append('\'');
        sb.append(", grade=").append(grade);
        sb.append(", childId=").append(childId);
        sb.append('}');
        return sb.toString();
    }

}

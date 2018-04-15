package com.elad.kstream.childdemo.join;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.TestResult;
import org.apache.kafka.streams.kstream.ValueJoiner;

/**
 * Created by eladw on 4/15/18.
 */
public class ChildAndTestResultJoiner implements ValueJoiner<TestResult, Child, ChildHightTest> {

    @Override
    public ChildHightTest apply(TestResult testResult, Child child) {
        System.out.println("Start join on id " + child.getId() + " test result id: " + testResult.getChildId());
        ChildHightTest join = new ChildHightTest();
        join.setGrade(testResult.getGrade());
        join.setCategory(testResult.getCategory());
        join.setId(child.getId());
        join.setAge(child.getAge());
        join.setHight(child.getHight());
        return join;
    }
}



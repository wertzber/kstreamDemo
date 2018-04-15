package com.elad.kstream.childdemo.transformer;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.ChildHightTransformed;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * Created by eladw on 4/12/18.
 */
public class ChildTransformer implements ValueTransformer<Child, ChildHightTransformed>{


    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public ChildHightTransformed transform(Child child) {
        System.out.println("Starting trabsformer on id: " + child.getId());
        ChildHightTransformed childHightTransformed = new ChildHightTransformed();
        childHightTransformed.setHight(child.getHight());
        childHightTransformed.setAge(child.getAge());
        childHightTransformed.setId(child.getId());
        return childHightTransformed;
    }

    @Override
    public ChildHightTransformed punctuate(long timestamp) {
        return null;
    }

    @Override
    public void close() {

    }
}

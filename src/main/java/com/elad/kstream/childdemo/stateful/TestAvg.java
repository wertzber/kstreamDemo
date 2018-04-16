package com.elad.kstream.childdemo.stateful;

/**
 * Created by eladw on 4/15/18.
 */
public class TestAvg {

    private Double avg;
    private int numOfResults;

    public TestAvg() {
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public int getNumOfResults() {
        return numOfResults;
    }

    public void setNumOfResults(int numOfResults) {
        this.numOfResults = numOfResults;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestAvg{");
        sb.append("avg=").append(avg);
        sb.append(", numOfResults=").append(numOfResults);
        sb.append('}');
        return sb.toString();
    }
}

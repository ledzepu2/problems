package statistics;

import utils.StatsUtil;

/**
 * Created by menona on 12/11/15.
 */
public class StatisticsNaiveGenerator implements Statistic {

    private int[] sample;
    private int counter;

    public StatisticsNaiveGenerator(int size)
    {
        sample = new int[size];
        counter = 0;
    }

    public void event(int value) {
           // System.out.println("counter "+counter+" "+Thread.currentThread().getName());
            sample[counter] = value;
            counter++;
    }

    public float mean() {


        return StatsUtil.calculatMean(sample);
    }

    public int minimum() {

        return StatsUtil.calculateMin(sample);
    }

    public int maximum() {
        return StatsUtil.calculatMaximum(sample);

    }

    public float variance() {

        return StatsUtil.doVarianceImperatively(sample);

    }


}

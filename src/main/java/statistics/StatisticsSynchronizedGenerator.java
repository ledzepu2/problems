package statistics;

import utils.StatsUtil;

/**
 * Created by menona on 12/11/15.
 */
public class StatisticsSynchronizedGenerator implements Statistic {

    private int[] sample;
    private int counter;

    public StatisticsSynchronizedGenerator(int size)
    {
        sample = new int[size];
        counter = 0;
    }

    public void event(int value) {
        synchronized (this)
        {
            //System.out.println("counter "+counter+" "+Thread.currentThread().getName());
            sample[counter] = value;
            counter++;
        }

    }

    public float mean() {

        synchronized (this)
        {
            return StatsUtil.calculatMean(sample);
        }

    }

    public int minimum() {
        synchronized (this)
        {
            return StatsUtil.calculateMin(sample);
        }
    }

    public int maximum() {
        synchronized (this)
        {
            return StatsUtil.calculatMaximum(sample);
        }

    }

    public float variance() {
        synchronized (this)
        {
            return StatsUtil.doVarianceViaStream(sample);
        }

    }


}

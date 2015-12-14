package statistics;

import utils.StatsUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by menona on 12/11/15.
 */
public class StatisticsLocksGenerator implements Statistic {

    private int[] sample;
    private int counter;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();


    public StatisticsLocksGenerator(int size)
    {
        sample = new int[size];
        counter = 0;
    }

    public void event(int value) {
            //System.out.println("counter "+counter+" "+Thread.currentThread().getName());
            writeLock.lock();
            try
            {
                sample[counter] = value;
                counter++;
            }
            finally {
                writeLock.unlock();
            }


    }

    public float mean() {

        readLock.lock();
        try
        {
            return StatsUtil.calculatMean(sample);
        }
        finally {
            readLock.unlock();
        }

    }

    public int minimum() {
        readLock.lock();
        try
        {
            return StatsUtil.calculateMin(sample);
        }finally {
            readLock.unlock();
        }

    }

    public int maximum() {
        readLock.lock();
        try
        {    return StatsUtil.calculatMaximum(sample);
        }finally {
            readLock.unlock();
        }

    }

    public float variance() {
        readLock.lock();
        try
        {
            return StatsUtil.doVarianceViaStream(sample);
        }finally {
            readLock.unlock();
        }

    }


}

package statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by menona on 12/12/15.
 */
public class StatisticsDemo {
    public static void main(String[] args) {
        //multiThreadedNaive(5000,50,150);
        multiThreadedSynchronized(500000,50,150);
       multiThreadedLocks(500000,50,150);
        multiThreadedLocks(500000,50,150);
        multiThreadedLocks(500000,50,150);
        multiThreadedLocks(500000,50,150);
        multiThreadedLocks(500000,50,150);
        multiThreadedLocks(500000,50,150);

    }

    private static void singleThreaded(int size) {
        Statistic sG = new StatisticsNaiveGenerator(size);
        for (int i = 0; i < size; i++) {
            sG.event(i);
        }
        System.out.println(sG.maximum());
        System.out.println(sG.mean());
        System.out.println(sG.minimum());
        System.out.println(sG.variance());
    }

    private static void multiThreadedNaive(int sampleSize,int writerThreads,int readerThreads) {

        final CountDownLatch latch = new CountDownLatch(writerThreads+readerThreads);
        long startTime = System.nanoTime();
        Statistic sG = new StatisticsNaiveGenerator(sampleSize);
        executeTasks(sampleSize, writerThreads, readerThreads, sG,latch);
        waitforThreadstoFinishAndComputeTimeDifference(latch, startTime,"Naive Option");
        System.out.println("End Multi-Threaded-Naive-Phase");
    }

    private static void multiThreadedSynchronized(int sampleSize,int writerThreads,int readerThreads) {
        final CountDownLatch latch = new CountDownLatch(writerThreads+readerThreads);
        long startTime = System.nanoTime();
        Statistic sG = new StatisticsSynchronizedGenerator(sampleSize);
        executeTasks(sampleSize, writerThreads, readerThreads, sG,latch);
        waitforThreadstoFinishAndComputeTimeDifference(latch, startTime,"Synchronized Option");
        System.out.println("End Multi-Threaded-Synchronized-Phase");
    }




    private static void multiThreadedLocks(int sampleSize, int writerThreads, int readerThreads) {
        final CountDownLatch latch = new CountDownLatch(writerThreads+readerThreads);
        long startTime = System.nanoTime();
        Statistic sG = new StatisticsLocksGenerator(sampleSize);
        executeTasks(sampleSize, writerThreads, readerThreads, sG,latch);
        waitforThreadstoFinishAndComputeTimeDifference(latch, startTime,"Locks Option");
        System.out.println("End Multi-Threaded-Locks-Phase");

    }

    private static void executeTasks(int sampleSize, int writerThreads, int readerThreads, Statistic sG,CountDownLatch latch) {
        ExecutorService executor = Executors.newFixedThreadPool(writerThreads+readerThreads);
        List<Callable<Boolean>> tasks = new ArrayList<>(readerThreads+writerThreads);
        generateReaderThreads(readerThreads, sG, tasks,latch);
        generateWriterThreads(sampleSize, writerThreads, sG, tasks,latch);

        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private static void waitforThreadstoFinishAndComputeTimeDifference(CountDownLatch latch, long startTime,String text) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            long endTime = System.nanoTime();
            System.out.println("Elapsed time is "+(endTime - startTime)/1000000+" ms for "+text);
        }
    }

    private static void generateReaderThreads(int readerThreads, Statistic sG, List<Callable<Boolean>> tasks,CountDownLatch latch) {
        while(readerThreads > 0)
        {
            tasks.add(new Reader(sG,latch));
            readerThreads --;
        }
    }

    private static void generateWriterThreads(int size, int writerThreads, Statistic sG, List<Callable<Boolean>> tasks,CountDownLatch latch) {
        int quot = size/writerThreads;
        int offset = 0 ;
        while(writerThreads > 0)
        {
            Callable writer = new Writer(sG,quot,offset,latch);
            offset += quot;
            tasks.add(writer);
            writerThreads--;
        }
    }

    public static class Writer implements Callable<Boolean> {

        private final Statistic generator;
        private final int offset;
        private final int size;
        private final CountDownLatch latch;

        public Writer(Statistic generator,int size,int offset,CountDownLatch latch) {
            this.generator = generator;
            this.size = size;
            this.offset = offset;
            this.latch = latch;
        }

        @Override
        public Boolean call() {

            for (int i = offset; i < offset+size ; i++) {
                //System.out.println("Executing add by"+Thread.currentThread().getName());
                            generator.event(i);
            }
            latch.countDown();
            return true;

        }


    }

    public static class Reader implements Callable<Boolean> {

        private final Statistic generator;
        private final CountDownLatch latch;


        public Reader(Statistic generator,CountDownLatch latch) {
            this.generator = generator;

            this.latch = latch;
        }

        @Override
        public Boolean call() {


               System.out.println(generator.maximum()+" is the maximum by "+Thread.currentThread().getName());
               System.out.println(generator.minimum()+" is the minimum by "+Thread.currentThread().getName());
               System.out.println(generator.mean()+" is the mean by "+Thread.currentThread().getName());
               System.out.println(generator.variance()+ " is the variance by "+Thread.currentThread().getName());
               latch.countDown();
               return true;


        }
    }
}

package statistics.ringbuffer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by menona on 12/13/15.
 */
public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        LongEventFactory longEventFactory = new LongEventFactory();
        int bufferSize = 4096;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(longEventFactory,bufferSize,executor);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            producer.onData(bb);
            //Thread.sleep(100);
        }
    }
}

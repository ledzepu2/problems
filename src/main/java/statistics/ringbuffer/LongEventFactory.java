package statistics.ringbuffer;

import com.lmax.disruptor.EventFactory;

/**
 * Created by menona on 12/13/15.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}

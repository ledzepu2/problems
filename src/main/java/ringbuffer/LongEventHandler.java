package ringbuffer;

import com.lmax.disruptor.EventHandler;

/**
 * Created by menona on 12/13/15.
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("Event:" + longEvent);
    }
}

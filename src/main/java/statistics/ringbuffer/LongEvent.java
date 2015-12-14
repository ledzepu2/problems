package statistics.ringbuffer;

/**
 * Created by menona on 12/13/15.
 */
public class LongEvent {
    private long event;

    public void setEvent(long event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "event=" + event +
                '}';
    }
}

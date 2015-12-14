package fileSearcher.consumer;

import config.Constants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by menona on 12/14/15.
 */
@Component
public class BootStrappingConsumer  {

    @Autowired
    private CopyConsumer copyConsumer;

    @Autowired
    private Logger logger;

    private boolean firstCallMade = true;

    @Scheduled(fixedRate = Constants.FIXED_RATE_DURATION)
    public void startConsumer() throws InterruptedException {

        if(firstCallMade)
        {
            logger.info("Executing copy process");
            firstCallMade = false;
            copyConsumer.startFileProcessing();
        }
        else
        {
            logger.info("Exiting .First time already executed....");
        }


    }

}

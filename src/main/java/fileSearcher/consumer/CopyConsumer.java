package fileSearcher.consumer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Created by menona on 12/13/15.
 */
@Component
public class CopyConsumer {

    @Autowired
    private Logger log;
    @Autowired
    private EventBus eventBus;

    private Path scan_directory;

    @Value("${file.scanLocation}")
    public String scanLocation;

    @Autowired
    private Logger logger;


  // @Scheduled(fixedDelay = Constants.FIXED_RATE_DURATION)
    @PostConstruct
    public void startFileProcessing()
    {
        scan_directory = Paths.get(scanLocation);
        logger.info("Loader service processing files  from  ..." + scan_directory.toString() + " at " + LocalDateTime.now());

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(scan_directory)) {
            for (Path path : ds) {
                //logger.info("processing path "+path);
                 eventBus.notify("souce.folder.traverse", Event.wrap(path));
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }



}

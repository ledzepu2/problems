package fileSearcher.processor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by menona on 12/13/15.
 */
@Consumer
public class SourceChecker {

    @Autowired
    private Logger log;
    @Autowired
    private EventBus eventBus;

    @Selector("souce.folder.traverse")
    public void search(Event<Path> filePathEvent)
    {
        Path inputPath = filePathEvent.getData();
        //log.info("path recvd by searcher"+inputPath);
        if (Files.isDirectory(inputPath))
        {
          //  log.info("the path"+inputPath+" is a directory");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputPath)) {
                for (Path path : stream) {
            //        System.out.println(path+"found by thread"+Thread.currentThread().getName()+" Directing to search.path.process channel");
                    eventBus.notify("souce.folder.traverse", Event.wrap(path));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        if (Files.isRegularFile(inputPath))
        {
          //  log.info("This path needs to be checked in model"+inputPath);
            eventBus.notify("source.folder.file.initiate", Event.wrap(inputPath));
        }
    }
}

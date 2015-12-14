package fileSearcher.processor;

import config.Constants;
import fileSearcher.event.PathSearchEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by menona on 12/13/15.
 */

@Consumer
public class SearchInitiator {

    @Autowired
    private Logger log;
    @Autowired
    private EventBus eventBus;

    @Value("${file.sourceLocation}")
    public String sourceLocation;

    @Value("${file.destLocation}")
    public String destLocation;

    @Selector("source.folder.file.initiate")
    public void search(Event<Path> filePathEvent)
    {
        Path inputPath = filePathEvent.getData();
        Path truncatedPath = inputPath.subpath(Constants.PATH_IGNORE,inputPath.getNameCount());
        Path searchPath = Paths.get("src").resolve(truncatedPath);
        Path sourcePath = Paths.get(sourceLocation);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath)) {
            for (Path path : stream) {
            log.info("looking for pattern"+inputPath+" in "+path);
             eventBus.notify("model.folder.traverse", Event.wrap(prepareFileSearchEvent(sourcePath,searchPath)));
    }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private PathSearchEvent prepareFileSearchEvent(Path sourcePath, Path searchPath) {
        return new PathSearchEvent(sourcePath,searchPath);
    }


}

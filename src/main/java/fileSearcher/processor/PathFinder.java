package fileSearcher.processor;

import fileSearcher.event.PathSearchEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by menona on 12/13/15.
 */

@Consumer
public class PathFinder {

    @Autowired
    private Logger log;
    @Autowired
    private EventBus eventBus;

    @Value("${file.destLocation}")
    public String destLocation;


    @Selector("model.folder.traverse")
    public void visitRecursively(Event<PathSearchEvent> pathSearchEventEvent) throws IOException {
        FileSearcher fileSearcher = new FileSearcher(pathSearchEventEvent.getData().getSearchPath(), Paths.get(destLocation));
        Files.walkFileTree(pathSearchEventEvent.getData().getSourcePath(), fileSearcher);

    }

}

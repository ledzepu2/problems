package fileSearcher.event;

import java.nio.file.Path;

/**
 * Created by menona on 12/13/15.
 */
public class PathSearchEvent {

    final Path searchPath;
    final Path sourcePath;

    public PathSearchEvent(Path sourcePath,Path searchPath) {
        this.sourcePath = sourcePath;
        this.searchPath = searchPath;
    }

    public Path getSearchPath() {
        return searchPath;
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    @Override
    public String toString() {
        return "PathSearchEvent{" +
                "searchPath=" + searchPath +
                ", sourcePath=" + sourcePath +
                '}';
    }
}

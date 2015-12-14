package fileSearcher.processor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by menona on 12/14/15.
 */
public class FileSearcher implements FileVisitor<Path> {

    private static final String TAB = "    ";
    private static final int TAB_SIZE = TAB.length();
    private  final Path searchPath;



    public FileSearcher(Path searchPath) {

        this.searchPath = searchPath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


     if (file.getFileName().equals(searchPath.getFileName()))
     {
         System.out.println("Found match for "+searchPath+" in path"+file);
         return FileVisitResult.TERMINATE;
     }
        return FileVisitResult.CONTINUE;



    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public void printSummary() {
    }

}
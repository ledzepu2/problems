package fileSearcher.processor;

import config.Constants;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by menona on 12/14/15.
 */
public class FileSearcher implements FileVisitor<Path> {


    private  final Path searchPath;
    private  final Path destPath;


    public FileSearcher(Path searchPath,Path destPath) {

        this.searchPath = searchPath;
        this.destPath = destPath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        /*Path truncatedSourceDirPath = dir.subpath(Constants.PATH_IGNORE,dir.getNameCount());
        Path copyPathDir = destPath.resolve(truncatedSourceDirPath);
        if(Files.notExists(copyPathDir))
        {
            Files.createDirectories(copyPathDir);
        }*/

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
         return processCopy(file);
     }
        return FileVisitResult.CONTINUE;

    }



    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    private FileVisitResult processCopy(Path file)  {
        Path truncatedSourcePath = file.subpath(Constants.PATH_IGNORE,file.getNameCount());
        Path copyPath = destPath.resolve(truncatedSourcePath);
       // System.out.println("Location to copy parent"+copyPath.getParent());
        try {

                if (!Files.exists(copyPath.getParent()))
                {
                    Files.createDirectories(copyPath.getParent());
                    System.out.println("Directory "+copyPath.getParent()+" not present ..Creating");
                }
            Files.copy(file,copyPath,StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            System.out.println("File "+file+" copy process exception");
        }
        catch (NoSuchFileException e2){
            System.out.println("File "+file+" copy process exception");
        }
        catch (IOException e1){
            e1.printStackTrace();
        }
        return FileVisitResult.TERMINATE;
    }


}
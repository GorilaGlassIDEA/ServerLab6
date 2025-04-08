package by.dima.model.service.files.io.read;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileFiles implements ReadableFile {
    private String contentFile;
    @Setter
    @Getter
    private String fileName;

    public ReadFileFiles(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getContent() throws IOException{

        contentFile = Files.readString(Paths.get(fileName));
        return contentFile;
    }
}

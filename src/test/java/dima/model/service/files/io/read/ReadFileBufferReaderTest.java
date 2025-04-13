package dima.model.service.files.io.read;

import by.dima.model.data.services.files.io.read.ReadFileBufferReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileBufferReaderTest {

    @Test
    void getContentTest() throws IOException {
        String defaultPathString = "/Users/dmitrijmartynov/IdeaProjects/ServerLab6/src/main/resources/newFile.txt";
        Path defaultPath = Path.of(defaultPathString);
        String newContent = "defaultContent\n dsfsd";
        Files.createFile(defaultPath);
        Files.writeString(defaultPath, newContent);
        ReadFileBufferReader reader = new ReadFileBufferReader(defaultPathString);
        try {
            assertEquals(newContent, reader.getContent());
        } finally {
            Files.deleteIfExists(defaultPath);
        }

    }
}
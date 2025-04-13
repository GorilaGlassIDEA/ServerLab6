package dima.model.service.files.io.write;

import by.dima.model.data.services.files.io.write.WriteFileOutputStreamWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WriteFileOutputStreamWriterTest {

    @Test
    void writeToFileTest() throws IOException {

        String pathToString = "/Users/dmitrijmartynov/IdeaProjects/lab5/src/main/resources/example.txt";
        Path pathTo = Path.of(pathToString);
        WriteFileOutputStreamWriter writeFileOutputStreamWriter = new WriteFileOutputStreamWriter(pathToString);
        String defaultContent = "defaultContent";
        writeFileOutputStreamWriter.write(defaultContent);
        assertEquals(defaultContent, Files.readString(pathTo));

    }
}
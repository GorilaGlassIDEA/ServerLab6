package by.dima.model.service.files.io.read;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileBufferReader implements ReadableFile {
    private String contentFromPath = "";
    @Getter
    private final String fileName;

    public ReadFileBufferReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getContent() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String newLine;
            while ((newLine = bufferedReader.readLine()) != null) {
                contentFromPath += newLine + "\n";
            }
            contentFromPath = contentFromPath.trim();
        } catch (IOException e) {
            System.err.println("Не прочитать файл!");
        }
        return contentFromPath;
    }
}

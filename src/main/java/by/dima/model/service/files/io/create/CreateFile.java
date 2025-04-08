package by.dima.model.service.files.io.create;

import by.dima.model.service.files.io.write.WriteableFile;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFile implements Creatable {
    @Setter
    private String defaultContent = "";
    private final WriteableFile writeableFile;
    private final String path;

    public CreateFile(WriteableFile writeableFile) {
        this.path = writeableFile.getPathTo();
        this.writeableFile = writeableFile;
    }

    @Override
    public void fileCreator() {
        Path filePath = Paths.get(path);

        System.out.println("CreateFile: " + path);

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                writeableFile.write(defaultContent);
            } catch (IOException e) {
                System.err.println("Impossible to create file!");
            }
        }

    }
}

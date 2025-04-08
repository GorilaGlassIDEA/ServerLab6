package by.dima.model.service.files.io.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriteFileFiles extends WriteFileModel {


    public WriteFileFiles(String pathTo) {
        super(pathTo);
    }

    @Override
    public boolean write(String content) {
        try {
            Files.writeString(Paths.get(getPathTo()), content);
            return true;
        } catch (IOException e) {
            System.err.println("Не удалось записать контент в файл по пути " + getPathTo());
            return false;
        }
    }
}

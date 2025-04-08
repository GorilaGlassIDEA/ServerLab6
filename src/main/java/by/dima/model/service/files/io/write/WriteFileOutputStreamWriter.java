package by.dima.model.service.files.io.write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteFileOutputStreamWriter extends WriteFileModel {


    public WriteFileOutputStreamWriter(String pathTo) {
        super(pathTo);
    }

    @Override
    public boolean write(String content) {

        try (OutputStream outputStream = new FileOutputStream(getPathTo())) {
            if (Files.exists(Path.of(getPathTo()))) {
                byte[] bytes = content.getBytes();
                outputStream.write(bytes);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Не удается записать данные в файл!");
            return false;
        }


    }

}

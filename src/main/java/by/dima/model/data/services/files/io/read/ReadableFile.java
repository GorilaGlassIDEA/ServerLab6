package by.dima.model.data.services.files.io.read;

import java.io.IOException;

public interface ReadableFile {
    String getContent() throws IOException;

    String getFileName();
}

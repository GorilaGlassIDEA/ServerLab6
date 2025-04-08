package by.dima.model.service.files.io.write;

// переписать с возможностью дописывания, а не переписывания текста в файле
public interface WriteableFile {
    boolean write(String content);

    String getPathTo();
}

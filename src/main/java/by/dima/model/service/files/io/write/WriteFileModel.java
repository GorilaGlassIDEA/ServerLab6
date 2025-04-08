package by.dima.model.service.files.io.write;

import lombok.Data;


@Data
abstract class WriteFileModel implements WriteableFile {
    private String pathTo;


    protected WriteFileModel(String pathTo) {
        this.pathTo = pathTo;
    }

    @Override
    public abstract boolean write(String content);
}

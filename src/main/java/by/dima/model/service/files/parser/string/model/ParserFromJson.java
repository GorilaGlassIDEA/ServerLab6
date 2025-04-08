package by.dima.model.service.files.parser.string.model;

public interface ParserFromJson<T>{
    T getModels(String jsonContent);
}

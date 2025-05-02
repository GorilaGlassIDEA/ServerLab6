package by.dima.model.data.services.files.parser.string.model;

import by.dima.model.data.abstracts.model.CollectionDTO;

public interface ParserToJson<T> {
    String getJson(T models);
}

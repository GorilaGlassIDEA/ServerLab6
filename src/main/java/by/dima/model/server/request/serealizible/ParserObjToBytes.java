package by.dima.model.server.request.serealizible;

import java.nio.ByteBuffer;

public interface ParserObjToBytes<T> {
    ByteBuffer getBytes(T t);

}

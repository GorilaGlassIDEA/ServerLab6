package by.dima.model.server.request.serealizible;

import java.nio.ByteBuffer;

public interface ParserBytesToObj<T> {
    T getObj(ByteBuffer byteBuffer);
}

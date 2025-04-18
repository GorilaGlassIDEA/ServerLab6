package by.dima.model.server.request.serealizible;

import java.nio.ByteBuffer;

public interface ParserBytesToObj<T> {
    T getObj();

    void setByteBuffer(ByteBuffer byteBuffer);
}

package by.dima.model.server.request.serealizible;

import by.dima.model.common.AnswerDTO;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class ParserAnswerDTOToBytes implements ParserObjToBytes<AnswerDTO> {

    private final Logger logger;

    public ParserAnswerDTOToBytes(Logger logger) {
        this.logger = logger;
    }

    @Override
    public ByteBuffer getBytes(AnswerDTO answerDTO) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(answerDTO);
            byteBuffer = ByteBuffer.wrap(bos.toByteArray());
            return byteBuffer;
        } catch (IOException e) {
            return byteBuffer.flip();
        }
    }
}

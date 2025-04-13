package dima.model.service.iterator;

import by.dima.model.data.services.iterator.TextIterable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class LineStringIteratorTest {

    @Test
    void iterationTextTest() {
        String line1 = "1111";
        String line2 = "2222";
        String line3 = "3333";


        String defaultText = line1 + "\n" + line2 + "\n" + line3;
        TextIterable textIterable = new TextIterable(defaultText);
        List<String> aArrWithLine = new ArrayList<>();
        for (String line : textIterable) {
            aArrWithLine.add(line);
        }
        assertEquals(line1, aArrWithLine.get(0));
        assertEquals(line2, aArrWithLine.get(1));
        assertEquals(line3, aArrWithLine.get(2));


    }
}
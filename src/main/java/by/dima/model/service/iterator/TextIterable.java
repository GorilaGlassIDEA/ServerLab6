package by.dima.model.service.iterator;

import java.util.Iterator;

public class TextIterable implements Iterable<String> {
    private LineStringIterator iterator;

    public TextIterable(String text) {
        if (text != null) {
            text = text.strip();
            iterator = new LineStringIterator(text);

        }
    }

    @Override
    public Iterator<String> iterator() {
        return iterator;
    }
}

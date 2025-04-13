package by.dima.model.data.services.iterator;

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

package dima.model.data.route.model.main;

import by.dima.model.common.route.sub.NormalizeValueImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormalizeValueImplTest {
    List<Number> numbers;
    NormalizeValueImpl normalizeValue;

    @BeforeEach
    void init() {
        numbers = List.of(1, 2, 3, 4, 5);
        normalizeValue = new NormalizeValueImpl();
        normalizeValue.setNumbers(numbers);

    }

    @Test
    void normalizeValueTest() {
        assertEquals(0.75D, normalizeValue.normalize(4));
    }

    @Test
    void normalizeValueWithOtherValues() {
        List<Number> numbers = List.of(10D, 2L, 100F);
        normalizeValue.setNumbers(numbers);

        assertEquals(0D, normalizeValue.normalize(2));
        assertEquals(1D, normalizeValue.normalize(100));

    }

}
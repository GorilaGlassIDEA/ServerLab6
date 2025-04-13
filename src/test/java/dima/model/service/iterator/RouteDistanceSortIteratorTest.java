package dima.model.service.iterator;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.iterator.RouteDistanceSortIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RouteDistanceSortIteratorTest {
    @Mock
    Route example1;
    @Mock
    Route example2;
    @Mock
    Route example3;

    @InjectMocks
    Map<Long, Route> routeMap = new HashMap<>();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        when(example1.getDistance()).thenReturn(1.02d);
        when(example3.getDistance()).thenReturn(3.02d);
        when(example2.getDistance()).thenReturn(2.02d);


        routeMap.put(1L, example1);
        routeMap.put(2L, example3);
        routeMap.put(3L, example2);

    }

    @Test
    void iteratorSortTest() {
        RouteDistanceSortIterator sortIterator = new RouteDistanceSortIterator(routeMap);
        Double count = 3.02d;
        while (sortIterator.hasNext()) {
            Double distance = sortIterator.next().getDistance();
            assertEquals(count, distance);
            count-=1;
        }
    }


}
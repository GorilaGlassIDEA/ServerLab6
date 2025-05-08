package dima.model.service.iterator;


import by.dima.model.common.main.Route;
import by.dima.model.data.services.iterator.RouteIdSortIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RouteSortIteratorTest {

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

        when(example1.getId()).thenReturn(1L);
        when(example3.getId()).thenReturn(3L);
        when(example2.getId()).thenReturn(2L);


        routeMap.put(example1.getId(), example1);
        routeMap.put(example3.getId(), example3);
        routeMap.put(example2.getId(), example2);

    }

    @Test
    void iteratorSortTest() {
        RouteIdSortIterator sortIterator = new RouteIdSortIterator(routeMap);
        Long count = 1L;
        while (sortIterator.hasNext()) {
            Long id = sortIterator.next().getId();
            assertEquals(count, id);
            count++;
        }
    }

}
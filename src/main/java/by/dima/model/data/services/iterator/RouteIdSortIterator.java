package by.dima.model.data.services.iterator;

import by.dima.model.common.main.Route;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RouteIdSortIterator implements Iterator<Route> {

    private int count;
    private final List<Long> keys;
    private final Map<Long, Route> routeMap;


    public RouteIdSortIterator(Map<Long, Route> routeMap) {
        this.keys = new ArrayList<>(routeMap.keySet());
        this.routeMap = routeMap;
        keys.sort(Long::compare);
        count = 0;
    }

    @Override
    public boolean hasNext() {
        return count < routeMap.size();
    }

    @Override
    public Route next() {
        return routeMap.get(keys.get(count++));
    }
}

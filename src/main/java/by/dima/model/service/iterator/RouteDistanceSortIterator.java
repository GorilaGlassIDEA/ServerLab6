package by.dima.model.service.iterator;

import by.dima.model.data.route.model.main.Route;

import java.util.*;

public class RouteDistanceSortIterator implements Iterator<Route> {
    private int count;
    private final List<Route> routeList;


    public RouteDistanceSortIterator(Map<Long, Route> routeMap) {
        this.routeList = new ArrayList<>(routeMap.values());
        count = 0;
        routeList.sort(new Comparator<>() {
            @Override
            public int compare(Route o1, Route o2) {
                return Double.compare(o2.getDistance(), o1.getDistance());
            }
        });
    }

    @Override
    public boolean hasNext() {
        return count < routeList.size();
    }

    @Override
    public Route next() {
        return routeList.get(count++);
    }
}

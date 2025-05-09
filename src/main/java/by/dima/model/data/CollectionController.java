package by.dima.model.data;

import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.group.model.Groups;
import by.dima.model.common.route.main.Route;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class CollectionController {
    @Setter
    private Map<Long, Route> collectionForControl;
    private final CollectionDTO models;

    public CollectionController(CollectionDTO collectionDTO) {
        this.models = collectionDTO;
        this.collectionForControl = collectionDTO.getRoutesMap();
    }


    public void addElem(Route route) {
        if (route.getId() == -1L) {
            route.setId(getNextId());
        }
        models.addNewElement(route);
    }

    public void removeElem(Long key) {
        if (key != null) {
            if (collectionForControl.containsKey(key)) {
                collectionForControl.remove(key);
                if (models.sizeArray() == 0) {
                    resetModels();
                }
            }
        } else {
            System.err.println("Check your args!");
        }

    }

    public void resetModels() {
        models.reset();

    }

    public boolean updateElem(Route newRoute) {
        if (collectionForControl.containsKey(newRoute.getId())) {
            collectionForControl.replace(newRoute.getId(), newRoute);
            return true;
        } else {
            return false;

        }
    }

    public boolean existId(Long id) {
        return this.collectionForControl.containsKey(id);
    }

    public Long getNextId() {
        return collectionForControl.keySet().stream().max(Long::compare).orElse(0L) + 1;

    }

    public boolean replaceRouteForKey(Route route) {
        Long id = route.getId();
        if (existId(id)) {
            Route routeFromCollection = collectionForControl.get(id);
            if (route.compareTo(routeFromCollection) > 0) {
                this.collectionForControl.replace(id, route);

                return true;
            }
        }
        return false;
    }

    public Groups getObjGroup() {
        return new Groups(this);
    }

}

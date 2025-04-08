package by.dima.model.data;

import by.dima.model.data.abstracts.model.Models;
import by.dima.model.data.group.model.Groups;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.service.files.io.write.WriteableFile;
import by.dima.model.service.files.parser.string.model.ParserToJson;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class CollectionController {
    @Setter
    private Map<Long, Route> collectionForControl;
    private final Models models;
    private final WriteableFile writeableFile;
    private final ParserToJson parser;

    public CollectionController(Models models, WriteableFile writeableFile, ParserToJson parser) {
        this.models = models;
        this.writeableFile = writeableFile;
        this.parser = parser;
        this.collectionForControl = models.getRoutesMap();
    }


    public void addElem(Route route) {
        models.addNewElement(route);
        syncCollections();
    }

    public void removeElem(Long key) {
        if (key != null) {
            if (collectionForControl.containsKey(key)) {
                collectionForControl.remove(key);
                syncModels();
                if (models.sizeArray() == 0) {
                    resetModels();
                }
            }
        } else {
            System.err.println("Check your args!");
        }

    }

    private void syncCollections() {
        collectionForControl = models.getRoutesMap();
    }

    private void syncModels() {
        models.setRoutesMap(collectionForControl);
    }

    public void resetModels() {
        models.reset();
        syncCollections();
    }

    public boolean saveCollection() {
        return writeableFile.write(parser.getJson(models));
    }

    public void updateElem(Route newRoute) {
        if (collectionForControl.containsKey(newRoute.getId())) {
            collectionForControl.replace(newRoute.getId(), newRoute);
            syncModels();
        } else {
            System.err.println("Element which has this id doesn't exist!");
        }
    }

    public boolean existId(Long id) {
        return this.collectionForControl.containsKey(id);
    }

    public boolean replaceRouteForKey(Route route) {
        Long id = route.getId();
        if (existId(id)) {
            Route routeFromCollection = collectionForControl.get(id);
            if (route.compareTo(routeFromCollection) > 0) {
                this.collectionForControl.replace(id, route);
                syncModels();
                return true;
            }
        }
        return false;
    }

    public Groups getObjGroup() {
        return new Groups(this);
    }

}

package by.dima.model.data.services.generate.id;

import by.dima.model.data.CollectionController;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Data
public class IdGenerateMy implements IdGenerateble {
    private CollectionController collectionController;

    public IdGenerateMy(CollectionController collectionController) {
        this.collectionController = collectionController;
    }

    @Override
    public long generateId(Long... ids) {
        if (ids == null || ids.length == 0) {
            Long newId = 0L;
            try {
                List<Long> allId = new ArrayList<>(collectionController.getCollectionForControl().keySet());
                for (Long id : allId) {
                    if (id > newId) {
                        newId = id;
                    }
                }
                return ++newId;
            } catch (NoSuchElementException e) {
                return newId;
            }
        } else {
            return ids[0];
        }
    }
}
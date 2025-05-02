package by.dima.model.data.abstracts.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersCollectionDTO {
    private Map<Long, CollectionDTO> map;

    public void edit(Long id, CollectionDTO collectionDTO) {
        map.put(id, collectionDTO);
    }

    public boolean isExist(Long userId) {
        return map.containsKey(userId);
    }

    public CollectionDTO getCollection(Long id) {
        if (isExist(id)) {
            return map.get(id);
        }
        return null;
    }
}

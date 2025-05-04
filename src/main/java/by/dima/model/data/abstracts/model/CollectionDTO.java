package by.dima.model.data.abstracts.model;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Данный класс является оберткой структуры Map и используется для работы с элементами Map
 * Этот класс также необходим для правильной сериализации и десериализации объектов в json формат
 *
 * @see ParserToJson
 */
@Getter
@NoArgsConstructor
public class CollectionDTO {
    @Setter
    private Map<Long, Route> routesMap;
    private ZonedDateTime zonedDateTime;
    private final String type = Route.class.getName();

    public CollectionDTO(Map<Long, Route> routesMap) {
        this.routesMap = routesMap;
        this.zonedDateTime = ZonedDateTime.now();
    }

    /**
     * @return количество элементов в Map
     */
    public long sizeArray() {
        return routesMap.size();
    }

    /**
     * @param route элемент для добавления в Map структуру
     */
    public void addNewElement(Route route) {
        routesMap.put(route.getId(), route);
    }

    /**
     * удаляет все элементы из структуры
     */
    public void reset() {
        routesMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Models: \n" + routesMap;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CollectionDTO models = (CollectionDTO) o;
        return Objects.equals(routesMap, models.routesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routesMap);
    }
}

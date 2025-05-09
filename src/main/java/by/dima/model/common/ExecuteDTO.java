package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ExecuteDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Map<String, Route>> commandsForExecuteScript;
}
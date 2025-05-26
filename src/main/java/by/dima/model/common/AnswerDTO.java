package by.dima.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Данный класс нужен для отправки ответа клиенту
 */

@Data
@AllArgsConstructor
public class AnswerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String answer;
    private AuthList auth = AuthList.UNAUTHORIZED;

    public AnswerDTO() {
        answer = "Нет ответа от выполненной команды!";
    }

    public AnswerDTO(String answer) {
        this.answer = answer;
    }
}

package application.util;

import java.util.List;
import java.util.function.Function;

public class GenerateUniqueClass {
    /*
     * @param items       Список объектов.
     * @param idExtractor Лямбда для получения текущего ID из объекта.
     * @param <T>         Тип объекта.
     * @return Уникальный ID.
     */
    public static <T> int generateId(List<T> items, Function<T, Integer> idExtractor) {
        return items.stream()
                .map(idExtractor)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }
}

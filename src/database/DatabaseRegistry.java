package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DatabaseRegistry {
    private static final Map<Class<?>, Object> databases = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(Class<T> type) {
        return (List<T>) databases.computeIfAbsent(type, k -> new ArrayList<T>());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMap(Class<?> marker) {
        return (Map<K, V>) databases.computeIfAbsent(marker, k -> new HashMap<K, V>());
    }

    public static <T> T findOne(Class<T> type, Predicate<T> predicate) {
        return getList(type).stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public static <T> List<T> findAll(Class<T> type, Predicate<T> predicate) {
        return getList(type).stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static void clearAll() {
        databases.clear();
    }
}

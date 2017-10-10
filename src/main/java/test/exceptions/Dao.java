package test.exceptions;

import java.util.List;

public interface Dao<T> {

    T get(String id);

    List<T> findLike(T template);
}

package tv.toby.spring.week02.a.typetoken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class SimpleTypeSafeMap {

    private Map<Class<?>, Object> map = new HashMap<>();

    public <T> void put(Class<T> k, T v) {
        map.put(k, v);
    }

    public <T> T get(Class<T> k) {
        return k.cast(map.get(k));
    }
}

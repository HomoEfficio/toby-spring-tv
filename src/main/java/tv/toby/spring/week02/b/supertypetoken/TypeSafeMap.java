package tv.toby.spring.week02.b.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class TypeSafeMap {

    private Map<TypeReference<?>, Object> map = new HashMap<>();

    public <T> void put(TypeReference<T> k, T v) {
        map.put(k, v);
    }

    public <T> T get(TypeReference<T> k) {
        if (k.getType() instanceof Class<?>)
            return ((Class<T>)k.getType()).cast(map.get(k));
        else
            return ((Class<T>)((ParameterizedType)k.getType()).getRawType()).cast(map.get(k));
    }
}

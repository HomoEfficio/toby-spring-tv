package tv.toby.spring.week02.b.supertypetoken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public abstract class TypeReference<T> {

    private Type type;

    protected TypeReference() {

        Type superClass = getClass().getGenericSuperclass();

        this.type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;

        TypeReference<?> that = (TypeReference<?>) o;

        return type.equals(that.type);

    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}

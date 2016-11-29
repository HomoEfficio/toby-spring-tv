package tv.toby.spring.week02.week25;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by hanmomhanda on 2016-11-10.
 */
public abstract class TypeReference<T> {

    private Type type;

    protected TypeReference() {
        Type superClassType = getClass().getGenericSuperclass();
        if (!(superClassType instanceof ParameterizedType)) {  // sanity check
            throw new IllegalArgumentException("TypeReference는 항상 실제 타입 파라미터 정보와 함께 생성되어야 합니다.");
        }
        this.type = ((ParameterizedType)superClassType).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}

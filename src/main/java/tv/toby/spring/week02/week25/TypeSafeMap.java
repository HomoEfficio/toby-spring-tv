package tv.toby.spring.week02.week25;

import org.springframework.core.ResolvableType;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-30.
 */
public class TypeSafeMap {

    //            private Map<TypeReference<?>, Object> map = new HashMap<>();
//
//            public <T> void put(TypeReference<T> k, T v) {
//                map.put(k, v);
//            }
//
//            public <T> T get(TypeReference<T> k) {
//                if (k.getType() instanceof Class<?>)
//                    return ((Class<T>)k.getType()).cast(map.get(k));
//                else
//                    return ((Class<T>)((ParameterizedType)k.getType()).getRawType()).cast(map.get(k));
//            }

    // 인스턴스 메서드 안에서 생성된 이너클래스는 아우터클래스의 참조를 갖고 있으므로,
    // 아우터클래스의 참조가 계속 남아서 메모리 릭 우려..
    // (본 예제에서는  인스턴스 메서드가 아니라 스태틱인 main 메서드에서 생성하므로 다른 케이스임)
    // 따라서 아래와 같이 TypeReferenceMap의 Key 타입을 변경

    private Map<Type, Object> map = new HashMap<>();  // key로 사용되던 Class<?> 대신 Type로 변경

    public <T> void put(TypeReference<T> k, T v) {  // 수퍼 타입을 추출할 수 있는 TypeReference<T>를 인자로 받음
        map.put(k.getType(), v);  // key가 Type으로 바뀌었으므로 기존의 k 대신 k.getType()으로 변경
    }

    public <T> T get(TypeReference<T> k) {  // key로 TypeReference<T>를 사용하도록 수정
        if (k.getType() instanceof ParameterizedType)
//            return ((Class<T>)((ParameterizedType)k.getType()).getRawType()).cast(map.get(k.getType()));
            return (T)map.get(ResolvableType.forInstance(k).getSuperType().getGeneric(0));
        else
            return ((Class<T>)k.getType()).cast(map.get(k.getType()));
    }
}

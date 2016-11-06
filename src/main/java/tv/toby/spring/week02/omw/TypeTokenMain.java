package tv.toby.spring.week02.omw;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import tv.toby.spring.week02.a.typetoken.SimpleTypeSafeMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class TypeTokenMain {

    public static void main(String[] args) {

        class SimpleTypeSafeMap {

            private Map<Class<?>, Object> map = new HashMap<>();

            public <T> void put(Class<T> k, T v) {
                map.put(k, v);
            }

            public <T> T get(Class<T> k) {
                return k.cast(map.get(k));
            }
        }

        SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();

        simpleTypeSafeMap.put(String.class, "abcde");
        simpleTypeSafeMap.put(Integer.class, 123);

        // 타입 토큰을 이용해서 별도의 캐스팅 없이도 안전하다.
        String v1 = simpleTypeSafeMap.get(String.class);
        Integer v2 = simpleTypeSafeMap.get(Integer.class);

        System.out.println(v1);
        System.out.println(v2);

        // 수퍼 타입 토큰을 써보자
        class Super<T> {}

        class Sub extends Super<List<String>> {}

        Sub sub = new Sub();

        Type typeOfGenericSuperclass = sub.getClass().getGenericSuperclass();

        // 위의 세 줄을 한 줄로 표현하면
//        Type typeOfGenericSuperclass = new Super<List<String>>() {}.getClass().getGenericSuperclass();

        System.out.println(typeOfGenericSuperclass);

        Type actualType = ((ParameterizedType) typeOfGenericSuperclass).getActualTypeArguments()[0];

        System.out.println(actualType);

        // 아래 코드는 컴파일 에러 발생
//        simpleTypeSafeMap.put(actualType, Arrays.asList("a", "b", "c"));
    }
}

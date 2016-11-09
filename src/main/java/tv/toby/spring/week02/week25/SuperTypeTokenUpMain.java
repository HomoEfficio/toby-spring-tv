package tv.toby.spring.week02.week25;

import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class SuperTypeTokenUpMain {

    public static void main(String[] args) {

        abstract class TypeReference<T> {

            private Type type;

            protected TypeReference() {
                Type superClass = getClass().getGenericSuperclass();
                if (superClass instanceof Class<?>) { // sanity check
                    throw new IllegalArgumentException("TypeReference는 항상 실제  타입 파라미터 정보와 함께 생성되어야 합니다.");
                }
                this.type = ((ParameterizedType)superClass).getActualTypeArguments()[0];
            }

            public Type getType() {
                return type;
            }

//            @Override
//            public boolean equals(Object o) {
//                if (this == o) return true;
//                if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;
//
//                TypeReference<?> that = (TypeReference<?>) o;
//
//                return type.equals(that.type);
//
//            }
//
//            @Override
//            public int hashCode() {
//                return type.hashCode();
//            }
        }


        class TypeSafeMap {

            private Map<Type, Object> map = new HashMap<>();

            public <T> void put(TypeReference<T> k, T v) {
                map.put(k.getType(), v);
            }

            public <T> T get(TypeReference<T> k) {
                if (k.getType() instanceof Class<?>)
                    return ((Class<T>)k.getType()).cast(map.get(k.getType()));
                else
                    return ((Class<T>)((ParameterizedType)k.getType()).getRawType()).cast(map.get(k.getType()));
            }


            // 객체 자체를 키로 붙잡고 있으면 메모리 릭 우려..
            //
        }


        // SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();
        TypeSafeMap typeSafeMap = new TypeSafeMap();

        // simpleTypeSafeMap.put(String.class, "abcde");
        typeSafeMap.put(new TypeReference<String>() {}, "abcde");

        // simpleTypeSafeMap.put(Integer.class, 123);
        typeSafeMap.put(new TypeReference<Integer>() {}, 123);

        // 드디어 List<String> 을 쓸 수 있다!!
        // new TypeReference<List<String>>() {}를 사용해서 List<String>.class와 동일한 효과를!!
        typeSafeMap.put(new TypeReference<List<String>>() {}, Arrays.asList("A", "B", "C"));



        // 타입 토큰을 이용해서 별도의 캐스팅 없이도 안전하다.
        // String v1 = typeSafeMap.get(String.class);
        String v1 = typeSafeMap.get(new TypeReference<String>() {});

        //Integer v2 = typeSafeMap.get(Integer.class);
        Integer v2 = typeSafeMap.get(new TypeReference<Integer>() {});

        List<String> listString = typeSafeMap.get(new TypeReference<List<String>>() {});


        System.out.println(v1);
        System.out.println(v2);
        System.out.println(listString);

        ResolvableType rt = ResolvableType.forInstance(new TypeReference<List<String>>(){});
        System.out.println(rt.getType());
        System.out.println(rt.getGeneric(0));
        System.out.println(rt.getNested(0));
        System.out.println(rt.getSuperType().getGenerics().getClass());
        System.out.println(rt.getSuperType().getGeneric(0).getType());
        System.out.println(rt.getSuperType().getGeneric(0).getNested(2).getType());

        System.out.println("=====");

        ResolvableType rt1 = ResolvableType.forInstance(new TypeReference<Map<Integer, String>>(){});
        System.out.println(rt1.getSuperType().getGeneric(0).getType());
        System.out.println(rt1.getSuperType().getGeneric(0).getNested(1).getType());
        System.out.println(rt1.getSuperType().getGeneric(0).getNested(2).getType());


    }
}

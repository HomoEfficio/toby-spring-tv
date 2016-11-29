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

//        abstract class TypeReference<T> {
//
//            private Type type;
//
//            protected TypeReference() {
//                Type superClassType = getClass().getGenericSuperclass();
//                if (!(superClassType instanceof ParameterizedType)) {  // sanity check
//                    throw new IllegalArgumentException("TypeReference는 항상 실제 타입 파라미터 정보와 함께 생성되어야 합니다.");
//                }
//                this.type = ((ParameterizedType)superClassType).getActualTypeArguments()[0];
//            }
//
//            public Type getType() {
//                return type;
//            }
//        }
//
//
//        class TypeSafeMap {
//
//            private Map<Type, Object> map = new HashMap<>();  // key로 사용되던 Class<?> 대신 Type로 변경
//
//            public <T> void put(TypeReference<T> k, T v) {  // 수퍼 타입을 추출할 수 있는 TypeReference<T>를 인자로 받음
//                map.put(k.getType(), v);  // key가 Type으로 바뀌었으므로 기존의 k 대신 k.getType()으로 변경
//            }
//
//            public <T> T get(TypeReference<T> k) {  // key로 TypeReference<T>를 사용하도록 수정
//                if (k.getType() instanceof ParameterizedType)
//                    return ((Class<T>)((ParameterizedType)k.getType()).getRawType()).cast(map.get(k.getType()));
//                else
//                    return ((Class<T>)k.getType()).cast(map.get(k.getType()));
//            }
//        }


        // SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();
        TypeSafeMap typeSafeMap = new TypeSafeMap();

        // simpleTypeSafeMap.put(String.class, "abcde");
        typeSafeMap.put(new TypeReference<String>() {}, "abcde");

        // simpleTypeSafeMap.put(Integer.class, 123);
        typeSafeMap.put(new TypeReference<Integer>() {}, 123);

        // 드디어 List<String> 을 쓸 수 있다!!
        // new TypeReference<List<String>>() {}를 사용해서 List<String>.class와 동일한 효과를!!
        typeSafeMap.put(new TypeReference<List<String>>() {}, Arrays.asList("A", "B", "C"));

        // List<List<String>> 처럼 중첩된 ParameterizedType도 사용 가능하다!!
        typeSafeMap.put(new TypeReference<List<List<String>>>() {},
                Arrays.asList(Arrays.asList("A", "B", "C"), Arrays.asList("a", "b", "c")));

        // Map<K, V>도 된다.
        Map<String, String> strMap1 = new HashMap<>();
        strMap1.put("Key1", "Value1");
        strMap1.put("Key2", "Value2");
        typeSafeMap.put(new TypeReference<Map<String, String>>() {}, strMap1);



        // 수퍼 타입 토큰을 이용해서 별도의 캐스팅 없이도 안전하다.
        // String v1 = typeSafeMap.get(String.class);
        String v1 = typeSafeMap.get(new TypeReference<String>() {});

        //Integer v2 = typeSafeMap.get(Integer.class);
        Integer v2 = typeSafeMap.get(new TypeReference<Integer>() {});

        // 바로 이거다!
        // List<String>.class 처럼 언어에서 지원해 주지 않는 클래스 리터럴을 사용하지 않고도
        // List<String>라는 타입을 쓸 수 있게 되었다.
        List<String> listString = typeSafeMap.get(new TypeReference<List<String>>() {});

        // List<List<String>> 처럼 중첩된 ParameterizedType도 사용 가능하다!!
        List<List<String>> listListString =
                typeSafeMap.get(new TypeReference<List<List<String>>>() {});

        // Map<K, V>도 된다.
        Map<String, String> strMap = typeSafeMap.get(new TypeReference<Map<String, String>>() {});


        System.out.println(v1);
        System.out.println(v2);
        System.out.println(listString);
        System.out.println(listListString);
        System.out.println(strMap);

        System.out.println("-----------------");

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

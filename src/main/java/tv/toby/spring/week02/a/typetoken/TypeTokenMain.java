package tv.toby.spring.week02.a.typetoken;

/**
 * Created by hanmomhanda on 2016-11-06.
 */
public class TypeTokenMain {

    public static void main(String[] args) {

        SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();

        simpleTypeSafeMap.put(String.class, "abcde");
        simpleTypeSafeMap.put(Integer.class, 123);

        // 타입 토큰을 이용해서 별도의 캐스팅 없이도 안전하다.
        String v1 = simpleTypeSafeMap.get(String.class);
        Integer v2 = simpleTypeSafeMap.get(Integer.class);

        System.out.println(v1);
        System.out.println(v2);

        // TypeToken 만으로는 아래와 같은 generic은 쓸 수 없다.
//        typeSafeMap.put(List<String>.class, Arrays.asList("a", "b", "c"));
    }
}

package tv.toby.spring.week04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class Generics {

    public static void main(String[] args) {

        class Hello<T> { // type parameter
            // 컴파일 시점의 타입 체크 가능


        }

        // 위험한 런타임 타입 캐스팅 방지 불가
        List mylist = new ArrayList();

        // Raw Type: 타입 파라미터 없는 List 같은 것들..
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawInts = ints;  // Raw Type
        @SuppressWarnings("unchecked")
        List<Integer> ints2 = rawInts;
        List<String> strs = rawInts; // 컴파일 에러 안난다. 하위 호환성
        String s = strs.get(0);  // 런타임 에러


        Hello<String> stringHello = new Hello<>(); // type argument

    }
}

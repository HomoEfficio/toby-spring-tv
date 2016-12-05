package tv.toby.spring.week04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class A_Generics {

    public static void main(String[] args) {

        class Hello<T> { // type parameter
            // 컴파일 시점의 타입 체크 가능

        }

        List<String> strList = new ArrayList<>();
//        strList.add(1);  // 바로 컴파일 에러

        // 위험한 런타임 타입 캐스팅 방지 불가
        List myList = new ArrayList();
        myList.add("str");
        Integer integer = (Integer)myList.get(0);  // 이런 불완전한 캐스팅을 컴파일 타임에 막지 못한다.


        // Raw Type: 타입 파라미터 없는 List 같은 것들..
        List ints = new ArrayList<Integer>();
        List rawInts = ints;  // Raw Type
        @SuppressWarnings("unchecked")
        List<Integer> ints2 = rawInts;  // 컴파일 에러 안난다.
        List<String> strs = rawInts; // 컴파일 에러 안난다. 하위 호환성. Warning은 뜬다.
        String s = strs.get(0);  // 런타임 에러


        Hello<String> stringHello = new Hello<>(); // type argument

    }
}

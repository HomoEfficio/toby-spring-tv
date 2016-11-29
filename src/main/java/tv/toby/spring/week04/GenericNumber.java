package tv.toby.spring.week04;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class GenericNumber {

    public static void main(String[] args) {

        Integer i = 10;
        Number n = i;

        List<Integer> intList = new ArrayList<>();
//        List<Number> numberList = intList; // 공변, 컴파일 에러
        // List<Integer>는 List<Number>의 서브 타입이 아니다.

        ArrayList<Integer> arrList = new ArrayList<>();
        List<Integer> integers = arrList; // 컴파일 에러 안난다.
//        ArrayList<Number> numList = arrList; // 컴파일 에러

    }
}

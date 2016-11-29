package tv.toby.spring.week04;

import java.io.Closeable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
//public class BoundedTypeParameter<T extends List> {
    //  T 의 범위에 대해 제약을 가한다.
public class BoundedTypeParameter<T> {


    static <T extends List> void printSingle(T t) {

    }

    static <T extends List & Serializable & Comparable & Closeable> void printMulti(T t) { // 여러가지 다 만족
        // OR는 없음
    }



    static long countGreaterThan(Integer[] arr, Integer elem) {
        return Arrays.stream(arr)
                .filter(s -> s > elem)
                .count();

    }

//    static <T> long countGreaterThan(T[] arr, T elem) {
//        return Arrays.stream(arr)
//                .filter(s -> s > elem) // 여기서 에러
//                .count();
//
//    }

    static <T extends Comparable<T>> long countGreaterThan(T[] arr, T elem) {
        return Arrays.stream(arr)
                // erasure가 안 날리는 것은 super 정보와 upperbound 정보
                // s.compareTo() 사용 가능
                .filter(s -> s.compareTo(elem) > 0)
                .count();

    }



    public static void main(String[] args) {

        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(countGreaterThan(arr, 4));

        String[] sarr = new String[]{ "a", "b", "c", "d", "e"};
        System.out.println(countGreaterThan(sarr, "b"));

    }
}

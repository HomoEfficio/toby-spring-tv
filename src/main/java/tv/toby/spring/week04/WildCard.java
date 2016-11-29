package tv.toby.spring.week04;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class WildCard {


    static <T> void method(List<? extends Comparable> t) {

    }

    static <T> void method1(List<?> t) {

    }

    static void printList(List<Object> list) {

        list.forEach(s -> System.out.println(s)); // s의 toString()에만 관심있다.
    }

    static void printList2(List<?> list) { // <? extends Object>와 같다.

        list.forEach(s -> System.out.println(s)); // s의 toString()에만 관심있다.

//        list.add(1); // 안된다
//        list.add("str"); // 안된다
//        list.add(null); // 이거만 된다.
        list.size(); // 등등 List의 메서드만 사용 가능

    }

    static void printList3(List<? extends Comparable> list) { // <? extends Object>와 같다.

        list.forEach(s -> System.out.println(s)); // s의 toString()에만 관심있다.

//        list.forEach(s -> s.compareTo()); // 컴파일 에러. 공변
    }

    public static void main(String[] args) {

        List<?> list; // wild card, 모른다, 관심없다. 나중에 정해지면 알고 사용하겠다.
        List<? extends Object> list1; // 이거랑 같다. Object에 있는 기능만 가져와서 사용하면 된다.

        printList(Arrays.asList(1, 2, 3));
        printList2(Arrays.asList(1, 2, 3, 4));

        // ?과 Object는 뭐가 다를까?
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
//        printList(list2); // 이건 컴파일 에러
        printList2(list2); // 이건 컴파일 에러 안남



        List<Integer> list3 = Arrays.asList(1, 2, 3);
        System.out.println(isEmpty(list3));

        List<Integer> list4 = Arrays.asList(1, 2, 3, 4, 3, 5, 3, 2);
        System.out.println(frequency(list4, 3));

        List<Integer> list5 = Arrays.asList(1);
        System.out.println(max(list5));

        List<Integer> list6 = Arrays.asList(1, 2, 3, 4, 3, 5, 3, 2);
        System.out.println(Collections.max(list6, (Comparator<Object>)(a, b) -> a.toString().compareTo(b.toString())));
    }


//    static <T> boolean isEmpty(List<T> list) {
//        return list.size() == 0;

    // 내부 구현이 외부로 드러난다
    // 내부 의도가 외부로 바르게 드러나지 않는다?
    // 이런 경우에는 ? 를 쓰는게 Java 철학에 맞는다
//    }

    static boolean isEmpty(List<?> list) { // ? 의 기능은 사용하지 않고 List 의 기능만 사용하겠다. 는 의도
        return list.size() == 0;

    }

//    static <T> long frequency(List<T> list, T t) { // both methods have same erasure
//        return list.stream()
//                .filter(s -> s.equals(t))
//                .count();
//    }

    static long frequency(List<?> list, Object t) { // ? t 라고 할 수는 없으므로 Object t 라고 한다.
        return list.stream()
                .filter(s -> s.equals(t))
                .count();
    }

//    private static <T extends Comparable<T>> T max(List<T> list) {
//        return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
//    }


    // 이게 더 나은..
    // 어떨 떄 상위 경계(서플라이로 만들어서 외부 메서드에서 사용된다면)를 사용하고 어떨 떄 하위 경계(컨수머에 쓸 떄)를 쓰나
    private static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
    }

}

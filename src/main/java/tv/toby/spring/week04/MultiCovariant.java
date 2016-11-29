package tv.toby.spring.week04;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class MultiCovariant {

    public static void main(String[] args) {

//        List<String> s1 = new MyList<String, Integer>(); // 에러 안남
//        List<String> s2 = new MyList<String, String>(); // 에러 안남



        method(1, Arrays.asList(1, 2, 3));

        MultiCovariant.<Integer>method(1, Arrays.asList(1, 2, 3)); // type witness


        List<String> strings = Collections.emptyList(); // 인자가 없는데도 추론 한다. 6, 7에서는 안 될수도
        List<String> strings1 = Collections.<String>emptyList(); // 인자가 없는데도 추론 한다. 6, 7에서는 이렇게 해야
    }

    static <T> void method(T t, List<T> list) {

    }

//    private static class MyList<T, T1> implements List<String> {
//    }
}

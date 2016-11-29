package tv.toby.spring.week04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-20.
 */
public class WildcardCapture {

    public static void main(String[] args) {

        List<?> list = Arrays.asList(1, 2, 4, 3, 7);

        System.out.println(reverse(list));
    }

    static Object reverse(List<?> list) {

//        List<?> temp = new ArrayList<>(list);
//
//        for (int i = 0 ; i < list.size() ; i++) {
//            list.set(i, temp.get(list.size() -i -1)); // 에러 나므로 Capture Helper를 써라. 캡쳐는 타입 유추라고 보자.
//        }

        return reverseHelper(list);
    }

    private static <T> List<T> reverseHelper(List<T> list) {
        List<T> temp = new ArrayList<>(list);

        for (int i = 0 ; i < list.size() ; i++) {
            list.set(i, temp.get(list.size() -i -1));
        }

        return list;
    }
}

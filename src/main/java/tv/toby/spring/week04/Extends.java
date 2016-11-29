package tv.toby.spring.week04;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class Extends {

    static class A{}
    static class B extends A {}

    static void print(List<? extends Object> list) {}

    public static void main(String[] args) {

        List<B> listB = new ArrayList<B>();

//        List<A> la = listB; // 이건 공변 에러

        List<? extends A> la2 = listB; // 이건 가능

        List<? super B> lsa = listB; // 이건 가능
//        lsa.add(new A()); // 이건 또 에러.. capture 어쩌고 에러..
        lsa.add(null); // 이건 또 되요..
//        lsa.add(new B()); // 이건 또 에러.. capture 어쩌고 에러..
        listB.add(new B()); // 이건 당연히 된다.

    }
}

package tv.toby.spring.week04;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class GenericMethods {

    public static void main(String[] args) {

        new GenericMethods().print("Hello");

        staticPrint("Static Hello");
    }

    <T> void print(T t) {
        System.out.println(t.toString());
    }

    static <T> void staticPrint(T t) {
        System.out.println(t.toString());
    }

    public <T> GenericMethods() {

    }


}

//public class Ccc<T> {
////    static void print(T t) {  // 이건 T를 식별할 수 없다.
//        // Ccc<T>의 T는 객체 생성시에 알 수 있으므로 static에서는 알 수 없다.
//
//    }
//}
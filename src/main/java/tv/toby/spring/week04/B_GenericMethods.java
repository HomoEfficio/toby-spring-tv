package tv.toby.spring.week04;

import java.io.IOException;

/**
 * Created by hanmomhanda on 2016-11-19.
 */
public class B_GenericMethods {

    public static void main(String[] args) {

        new B_GenericMethods().print("Hello");

        staticPrint("Static Hello");
    }

//    public void method(String s) {}
//    public boolean method(String s) { return false; }
//    public void method(String s) throws IOException {}

    <T> void print(T t) {
        System.out.println(t.toString());
    }

    static <T> void staticPrint(T t) {
        System.out.println(t.toString());
    }  // static 메서드에서도 사용 가능

    // 하지만 아래와 같은 방식은 불가
//public class Ccc<T> {  // 여기서의 T는 객체 생성 시에 알 수 있으므로 런타임 인스턴스에서만 식별 가능
////    static void print(T t) {  // 컴파일 타임에 T를 식별할 수 없으므로 static 에 사용할 수 없다..
//        // Ccc<T>의 T는 객체 생성시에 알 수 있으므로 static에서는 알 수 없다.
//
//    }
//}


    public <T> B_GenericMethods() {

    }


}


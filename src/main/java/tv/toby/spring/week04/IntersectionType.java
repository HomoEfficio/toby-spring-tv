package tv.toby.spring.week04;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by hanmomhanda on 2016-11-20.
 */
public class IntersectionType {

    public static void main(String[] args) {
        simpleLambda(s -> s);

        simpleLambda((Function)s -> s); // 람다식도 캐스팅 가능


        intersectionLambda((Function & Serializable & Cloneable)s -> s); // 인터페이스를 2개? 그럼 메서드도 최소 2개? 아니디.. 마커 인터페이스
        // 3개를 다 합쳐도 메서드가 하나라면 하나다. 예를 들어 Serializable이 extends Function 이라면 동일한 메서드가 2개가 되지만, 동일한 메서드니까 하나


        hello((Function & Hello & Hi) s-> s); //  default 말고 실제 메서드가 하나면 된다.



//        run((Function & Hello & Hi)s->s);

//        run((Function & Hello & Hi)s->s, o -> {
//            o.hello();
//            o.hi();
//        });

        // 람다도 일종의 익명 클래스가 생긴다.
    }

//    private static <T extends void run(T t, Consumer<T> consumer) {
//
//        consumer.accept(t);
//    }

//    private static void hello(Function function) {
//    }

    private static <T extends Function & Hello & Hi> void hello(T t) {

        t.hello();
        t.hi();
    }

//    private static void simpleLambda(Function o) {
//    }

    private static void simpleLambda(Function<String, String> o) {
    }

    private static void intersectionLambda(Serializable o) {
    }

    private static void intersectionLambda(Cloneable o) {
    }


    private static <T extends Function & Serializable & Cloneable> void intersectionLambda(T t) {

    }

    interface Hello {
        default void hello() {
            System.out.println("Hello");
        }
    }

    interface Hi {
        default void hi() {
            System.out.println("Hi");
        }
    }
}

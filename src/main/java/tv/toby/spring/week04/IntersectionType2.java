package tv.toby.spring.week04;

import java.util.function.Consumer;

/**
 * Created by hanmomhanda on 2016-11-20.
 */
public class IntersectionType2 {

    public static void main(String[] args) {
        run((DelegateTo<String> & Hello)() -> "omw", o -> {
            o.hello();
        });

        run((DelegateTo<String> & Uppercase)() -> "omw", o -> {
            o.hello();
        });
    }

    private static void run(DelegateTo<String> delegateTo) {
    }

    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    interface DelegateTo<T> {
        T delegate();
    }

    interface Hello extends DelegateTo<String> {

        default void hello() {
            System.out.println("Hello " + delegate());
        }
    }

    interface Uppercase extends DelegateTo<String> {

        default void hello() {
            System.out.println(delegate().toUpperCase());
        }
    }
}

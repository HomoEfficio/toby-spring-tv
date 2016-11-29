package tv.toby.spring.week05.reactive;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hanmomhanda on 2016-11-26.
 */
public class Ob {

    /*
    이벤트에 대응하는 방식의 프로그래밍
     */

    // 에릭 마이어
    // Duality: 쌍대성?
    // 옵저버 패턴
    // reactive streams - 표준 - Java9에는 표준 API로 추가

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);

        // List는 Iterable의 서브타입이다. Collections extends Iterable
        // Iterable은 foreaach loop의 타겟이 될 수 있다.

        // Iterable은 Iterator를 반환하는 iterator()를 가지고 있다.

        Iterable<Integer> iter0 = new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return null;
            }
        };

        Iterable<Integer> iter1 = () ->
                new Iterator<Integer>() {

                    int i = 0;
                    static final int MAX = 10;

                    @Override
                    public boolean hasNext() {
                        return i < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++i;
                    }
                };
        for (Integer i: iter1) {
            System.out.println(i);
        }

        System.out.println("------------");

        for (Iterator<Integer> it = iter1.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }

        System.out.println("======= 1부 끝");
        //------------- 1부 끝

        // Iterable <---> Observable (Duality)
        // Pull           Push
        // Observable, Publisher 데이터 또는 이벤트 소스 -> 타겟이 Observer, Subscriber
        // observer를 observable에 등록한다.

        class IntObservable extends Observable implements Runnable {
            // 소스
            @Override
            public void run() {
                for (int i = 0 ; i < 10 ; i++) {
                    setChanged();
                    notifyObservers(i);    // push
                    // int i = it.next()   // pull
                }
            }
        }

        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
//                System.out.println(arg);
                System.out.println(Thread.currentThread().getName() + " " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(observer);
        io.run();

        System.out.println("+++++++++++");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(io);

        System.out.println(Thread.currentThread().getName() + " EXIT");
        executorService.shutdown();
    }
}

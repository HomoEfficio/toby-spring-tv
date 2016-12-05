package tv.toby.spring.week05.reactive;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hanmomhanda on 2016-11-26.
 */
public class Reactive {

    // 옵저버 패턴의 한계
    // - complete 라는 개념이 없다.(notifyObservers() 밖에 없으므로..)
    // - 예외 처리(setChanged() 하다가 예외 발생하면?? 쓰레드 분리하고 나면 예외 처리가 쉽지 않다
    // - 분산, 병렬에서는 위의 한계가 악영향이 크다

    // reactive-streams.org
    //   readme.md#spec
    // Processor, Publisher, Subscriber, Subscription
    // reactivex.io - Rx* 시리즈들

    // projectreactor.io/old/reference 의 Figure 3

    // grpc - 두 개의 머신끼리의 통신 by google

    public static void main(String[] args) {

        // publisher <- observerable
        // subscriber    <- subscriber

        Iterable<Integer> iterable = Arrays.asList(1, 2, 3, 4);

        Publisher publisher = new Publisher() {

            public void subscribe(Subscriber subscriber) {

                Iterator<Integer> it = iterable.iterator();

                ExecutorService executorService = Executors.newCachedThreadPool();

                subscriber.onSubscribe(new Subscription() {
                    public void request(long n) {

                        Future<?> f = executorService.submit(() -> {
                            int i = 0;
                            // 여기에서 backpressure 를 조절
                            try {

                                while (i++ > 0) {

                                    if (it.hasNext())
                                        subscriber.onNext(it.next());
                                    else {
                                        subscriber.onComplete();
                                        break;
                                    }
                                }
                            } catch (RuntimeException e) {
                                subscriber.onError(e);
                            }
                        });
                    }

                    public void cancel() {

                    }
                });

            }
        };

        Subscriber<Integer> s = new Subscriber<Integer>() {

            Subscription subscription;

            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe: " + subscription);
//                subscription.request(Long.MAX_VALUE); // 다 내놔
                this.subscription = subscription;
                this.subscription.request(1); // 다 내놔
            }

            public void onNext(Integer item) {
                System.out.println("onNext: " + item);
                this.subscription.request(1);
            }

            public void onError(Throwable throwable) { // 트라이캐치 대신 여기
                System.out.println("onError: " + throwable);
            }

            public void onComplete() {
                System.out.println("onComplete");
            }

        };
    }
}

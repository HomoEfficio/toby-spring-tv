package tv.toby.spring.week08.spring.reactive;

import com.oracle.tools.packager.Log;

import java.nio.channels.CompletionHandler;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author homo.efficio@gmail.com
 *         create on 2016-12-17.
 */
public class FutureEx {

    public static void main(String[] args) throws InterruptedException, ExecutionException {


//        Thread.sleep(2000);
//        System.out.println("Hello");
//
//        System.out.println("Exit");
        // 별도의 스레드에서 비동기 작업을 실행하겠다.


        // 처음에는 하나도 안 만들고 사용하면서 생성해나간다.
        ExecutorService es = Executors.newCachedThreadPool();

//        es.execute(() -> {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//            }
//            Log.info("Async");
//        });
//
//        Log.info("Exit1");



//        Future<String> f = es.submit(() -> {
//            Thread.sleep(500);
//            Log.info("Async");
//            return "Hello";
//        });
//
//        // f.get() 이 blocking 하는 동안에 여기에 있는 코드는 실행 가능
//
//        System.out.println(f.get());  // f.get() 이 실행 완료될 때까지 block 된다.
//        Log.info("Exit");  // 가장 나중에 실행됨



        // callback 방식
//        FutureTask<String> futureTask = new FutureTask<String>(() -> {
//            Thread.sleep(500);
//            Log.info("Async");
//            return "Hello";
//        });
//
//        es.execute(futureTask);
//        es.shutdown();


        // 자바 비동기는 Future 또는 Callback



//        FutureTask<String> futureTask = new FutureTask<String>(() -> {
//            Thread.sleep(500);
//            Log.info("Async");
//            return "Hello";
//        }) {
//            @Override
//            protected void done() {
//                try {
//                    System.out.println(get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        es.execute(futureTask);
//        es.shutdown();




//        CallbackFutureTask f = new CallbackFutureTask(() -> {
//            Thread.sleep(500);
//            Log.info("Async");
//            return "Hello";
//        }, res -> System.out.println(res));
//
//        {
//            @Override
//            protected void done() {
//                try {
//                    System.out.println(get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        es.execute(futureTask);
//        es.shutdown();
//


        CallbackFutureTask f = new CallbackFutureTask(() -> {
            Thread.sleep(500);
            if (1==1) throw new RuntimeException("AsyncError");  // 주석 토글로 확인 요
            Log.info("Async");
            return "Hello";
        }, s -> System.out.println("Result: " + s)
        , e -> System.out.println("Error: " + e.getMessage()));

        es.execute(f);
        es.shutdown();
    }



    // Future 방식
    interface SuccessCallback {
        void onSuccess(String result);
    }

    interface ExceptionCallback {
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String> {

        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
            super(callable);
//            if (sc == null) throw null;
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done() {
            try {
                sc.onSuccess(get());
            } catch (InterruptedException e) {  // 작업을 수행하지 말고 종료해라
//                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {  // 비동기 안에서 예외 발생했다
//                e.printStackTrace();
                ec.onError(e.getCause());  // e는 한 번 wrapping 했기 때문에 e.getCause() 넘긴다
            }
        }
    }



    // 사례 AsynchronousByteChannel doc 참고
}

package tv.toby.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

@SpringBootApplication
@Slf4j
@EnableAsync
public class TobySpringTvApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(TobySpringTvApplication.class, args);
//	}




	@Component
	public static class MyService {
//		@Async
//		public String hello() throws InterruptedException {
////			.info("hello()");
//			Thread.sleep(500);
//			return "Hello"
//		}

		@Async
		public ListenableFuture<String> hello() throws InterruptedException {
			// biz 코드만 포함하게 바뀌었다.
			log.info("hello()");
			Thread.sleep(500);
			return new AsyncResult<>("Hello");
		}


//		@Async
//		public CompletableFuture<String> hello() throws InterruptedException {
//			// biz 코드만 포함하게 바뀌었다.
//			log.info("hello()");
//			Thread.sleep(500);
//			return new AsyncResult<>("Hello");
//		}


	}

	public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobySpringTvApplication.class, args)) {
//		};

		SpringApplication.run(TobySpringTvApplication.class, args);
	}

	@Autowired
	MyService myService;

	// 모든 빈이 준비된 후 자동으로 실행
//	@Bean
//	ApplicationRunner run() {
//		return args -> {
//			log.info("run()");
//			Future<String> f = myService.hello();
//			log.info("exit: " + f.isDone());
//			log.info("result: " + f.get());
//		};
//	}


	// 아래 빈 2개는 웹 앱 버전 이전에만 사용
//	@Bean
//	ApplicationRunner run() {
//		return args -> {
//			log.info("run()");
//			ListenableFuture<String> f = myService.hello();
//			f.addCallback(s -> System.out.println(s),
//					e -> System.out.println(e.getMessage()));
//			log.info("exit: " + f.isDone());
//			log.info("result: " + f.get());
//		};
//	}
//
////	@Async는 SimpleAsyncTaskExecutor는 새로운 스레드를 계속 만들므로
////	운영 환경에는 절대 사용하면 안된다. 아래와 같이 만들어서 사용하자.
//	@Bean
//	ThreadPoolTaskExecutor tp() {  // @Async("tp") 로 할 수도 있다.
//		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
//		te.setCorePoolSize(10);  // 초기에 10개 만들고
//		te.setQueueCapacity(200); //
//		te.setMaxPoolSize(100);  // 큐가 받쳐주지 못할 때 Max 까지 늘릴 수 있게 해준다.
//		te.setThreadNamePrefix("myThread");
//		return te;
//	}


	// 서블릿 3.1
	@RestController
	public static class MyController {

		@GetMapping("/blocking")
		public String blocking() throws InterruptedException {
			log.info("blocking");
			Thread.sleep(500);
			return "hello";
		}

		@GetMapping("/asyncContext")
		public String asyncContext(HttpServletRequest request) throws InterruptedException, ExecutionException {

			final AsyncContext asyncContext = request.startAsync();

			Future<String> hello = Executors.newSingleThreadExecutor().submit(() -> {
				log.info("async");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				asyncContext.complete();
				return "hello";
			});

			return hello.get();
		}

		@GetMapping("/callable")
		public Callable<String> callable() throws InterruptedException {
			return () -> {
				log.info("callable");
				Thread.sleep(500);
				return "hello";
			};
		}


		// 서블릿 스레드는 반환되고
		// dr은 대기중이다가 dr에 어떤 이벤트가 발생하면
		// 대기하던 dr이 바로 리턴된다.
		// 채팅방에 응용 가능
		// 워커 스레드를 따로 만들지 않는다.

		Queue<DeferredResult<String>> results = new ConcurrentLinkedDeque<>();
		@GetMapping("/dr")
		public DeferredResult<String> deferrable() throws InterruptedException {
			log.info("deferredResult");
			DeferredResult<String> dr = new DeferredResult<>();
			results.add(dr);
			return dr;
		}

		@GetMapping("/dr/count")
		public String drcount() {
			return String.valueOf(results.size());
		}

		@GetMapping("/dr/event")
		public String drevent(String msg) {
			for (DeferredResult<String> dr: results) {
				dr.setResult("Hello " + msg);
				results.remove(dr);
			}

			return "OK";
		}
	}




	// SSE 방식으로
	@GetMapping("/emitter")
	public ResponseBodyEmitter emitter() throws InterruptedException {
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();

		Executors.newSingleThreadExecutor().submit(() -> {
			for (int i = 0 ; i < 50 ; i++) {
				try {
					emitter.send("<p>Stream " + i + "</p>");
					Thread.sleep(100);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		return emitter;

	}





	/*
		서블릿 3.0
		서블릿 IO가 블록킹
		서블릿 스레드 하나에 IO 스레드도 하나만 할당
		스레드도 스택트레이스를 보유하므로 메모리 사용
		컨텍스트 스위칭 시 CPU 자원 많이 먹는다
		HttpServletReq/Res는 블로킹인 InputStream 방식
		중간에 외부 자원을 사용하는 blocking이 쓰면 컨텍스트 스위칭만 발생시키고 사실은 놀게됨
		큐에 쌓이고 대기, 레이턴시 증가, 처리율 낮아짐
		톰캣 기본 연결 스레드 수는 200개

		NIO 커넥터를 써도 결국 서블릿마다 스레드 하나 생성


		서블릿 3.1
		논블로킹 서블릿
		Callback 가능
		 */
}

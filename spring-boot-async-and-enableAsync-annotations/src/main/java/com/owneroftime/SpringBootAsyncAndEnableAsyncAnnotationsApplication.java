package com.owneroftime;

import com.owneroftime.app.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableAsync
public class SpringBootAsyncAndEnableAsyncAnnotationsApplication implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(SpringBootAsyncAndEnableAsyncAnnotationsApplication.class);

	@Autowired
	AppService appService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAsyncAndEnableAsyncAnnotationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Long> numberList = new ArrayList<>();
		numberList.add(15L);
		numberList.add(6L);
		numberList.add(10L);
		numberList.add(3L);

		List<Future<Long>> resultList = new ArrayList<>();

		// Submitting task for Asynchronous processing using threads
		numberList.forEach(number ->
				resultList.add(appService.computeFactorial(number)));

		// Doing Other work, while factorial is being computed
		for(int i = 10; i < 15; i++) {
			logger.info("Doing Other Work for value : {}", i);
			Thread.sleep(1); // Intentional Delay
		}

		// Waiting for Factorial computation completion
		// and logging the result
		resultList.forEach(result-> {
			try {
				logger.info("Computed Result : {}" , result.get());
			} catch (Exception e) {
				logger.error("Error Occurred !");
			}
		});
	}
}

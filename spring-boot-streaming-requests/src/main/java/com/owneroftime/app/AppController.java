package com.owneroftime.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/app")
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);

    /**
     * Method 1: Streaming Using ResponseBodyEmitter
     * URL      : http://localhost:8063/app/testResponseBodyEmitter
     */
    @GetMapping("/testResponseBodyEmitter")
    public ResponseEntity<ResponseBodyEmitter> testResponseBodyEmitter() {
        // ResponseBodyEmitter handles asynchronous response
        ResponseBodyEmitter responseBodyEmitter = new ResponseBodyEmitter();

        // Getting instance of execute service
        // newCachedTheadPool use concept of SunchronousQueue
        // Simply put. if a thread is waiting is queue it will get the next task
        // otherwise new thread is created by executer to handle the task
        ExecutorService executor = Executors.newCachedThreadPool();

        // Submiting task to executor
        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    // This is the response that will get emitted
                    responseBodyEmitter.send(i + " ");
                    // Intentional to mock delay
                    Thread.sleep(6);
                } catch (Exception exception) {
                    responseBodyEmitter.completeWithError(exception);
                }
            }
            // Once Entire response is sent, task is completed and thhreads are release.
            // This will mark the end of task
            responseBodyEmitter.complete();
        });
        return new ResponseEntity<>(responseBodyEmitter, HttpStatus.OK);
    }

    /**
     * Method 2 : Streaming Using StreamingResponseBody
     * URL      : http://localhost:8063/app/testStreamingResponseBody
     * @apiNote : It is highly recommended to configure TaskExecuter
     *            explicitly while using this option of Async processing
     */
    @GetMapping("/testStreamingResponseBody")
    public ResponseEntity<StreamingResponseBody> testStreamingResponseBody() {
        // StreamingResponseBody handles asynchronous response
        // It writes the content directly to output stream
        StreamingResponseBody streamingResponseBody = (outputStream) -> {
            for (int i = 0; i < 20; i++) {
                // This is the response that will get written directly to output stream
                outputStream.write((i + " ").getBytes());
                logger.info("Inside : {}" ,Thread.currentThread().getName());
                outputStream.flush();

                // Intentional to mock delay
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    outputStream.write("Error".getBytes());
                }
            }
            // Close the output stream
            outputStream.close();
        };
        return new ResponseEntity<>(streamingResponseBody, HttpStatus.OK);
    }
}

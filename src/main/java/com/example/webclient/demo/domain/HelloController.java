package com.example.webclient.demo.domain;

import com.example.webclient.demo.repsitory.User;
import com.example.webclient.demo.repsitory.Workpart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
    private static final String HOST = "http://localhost:8080/";

    private static final Log log = LogFactory.getLog(HelloController.class);

    @GetMapping("/slow-service-users")
    private List<User> getAllUsers() throws InterruptedException {
        Thread.sleep(2000L);
        return Arrays.asList(
                new User("restTemplate","pp@Sync")
                , new User("WebClient","ss@Async")
                , new User("sounds good to me","yy@POP"));
    }

    @GetMapping("/slow-service-workparts")
    public List<Workpart> getAllWorkPart() throws InterruptedException {
        Thread.sleep(3000L);
        return Arrays.asList(
                new Workpart("ps", "awesome")
                , new Workpart("my", "friend")
                , new Workpart("Thread", "Local")
                , new Workpart("MSA", "Cloud Native")
        );
    }

    @GetMapping(value = "/blocking2")
    public List<User> getBlocking2() {
        log.info("Starting BLOCKING");
        String uri = getSlowServiceUri(getType());
        var restTemplate = new RestTemplate();
        ResponseEntity<List<User>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> result = response.getBody();
        result.forEach(o -> log.info(o.toString()));
        log.info("Exiting BLOCKING");
        return result;
    }

    @GetMapping(value = "/blocking")
    public <T> List<T> getBlocking() {
        log.info("Starting BLOCKING");
        String uri = getSlowServiceUri(getType());
        var restTemplate = new RestTemplate();
        ResponseEntity<List<T>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<T>>() {});
        List<T> result = response.getBody();
        result.forEach(o -> log.info(o.toString()));
        log.info("Exiting BLOCKING");
        return result;
    }

    @GetMapping(value = "/non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public <T> Flux<T>  getNonBlocking(){
        log.info("Starting NON_BLOCKING");
        Flux<T> flux = (Flux<T>) WebClient.create()
                .get()
                .uri(getSlowServiceUri(getType()))
                .retrieve()
                .bodyToFlux(Object.class);
        flux.subscribe(obj -> log.info(obj.toString()));
        log.info("Exiting NON-BLOCKING");
        return flux;
    }

    private String getSlowServiceUri(boolean getA) {
        return getA ? HOST.concat("/slow-service-users") : HOST.concat("/slow-service-workparts");
    }

    private boolean getType(){
        double v = ((int)(Math.random() * 10)) % 2;
        return v == 1 ? true : false;
    }
}
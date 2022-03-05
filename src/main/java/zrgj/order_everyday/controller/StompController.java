package zrgj.order_everyday.controller;

import org.springframework.messaging.simp.annotation.SubscribeMapping;

public class StompController {
    @SubscribeMapping
    public void subscribe(String message) {
        System.out.println("Received: " + message);
    } 
}

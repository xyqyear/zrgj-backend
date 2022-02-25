package zrgj.order_everyday.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.service.OrderService;
import zrgj.order_everyday.util.ResponseWrapper;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping()
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> order(@RequestBody Map<String, Object> body) {
        return ResponseWrapper.wrap(orderService.newOrder((Integer) body.get("tableId"),
                (List<Map<String, Object>>) body.get("orderItems")));
    }
    
    @PutMapping("/checkout")
    public ResponseEntity<Object> checkout(@RequestBody Map<String, Integer> body) {
        return ResponseWrapper.wrap(orderService.checkout((Integer) body.get("orderId")));
    }

    @GetMapping("/ongoing")
    public ResponseEntity<Object> getOngoingOrders() {
        return ResponseWrapper.wrap(orderService.getOngoingOrders());
    }

    // TODO not working
    @GetMapping("/range")
    public ResponseEntity<Object> getOrdersInTimeRange(@RequestBody Map<String, Integer> body) {
        return ResponseWrapper.wrap(orderService.getOrdersInRange(body.get("from"), body.get("to")));
    }
}

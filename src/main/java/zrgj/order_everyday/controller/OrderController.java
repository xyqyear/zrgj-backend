package zrgj.order_everyday.controller;

import java.util.List;
import java.util.Map;

import com.auth0.jwt.interfaces.Claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.service.OrderService;
import zrgj.order_everyday.util.ResponseWrapper;
import zrgj.order_everyday.util.JWTUtil;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping()
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> order(@RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> body) {
        Map<String, Claim> userInfo = JWTUtil.getClaimsFromHeader(token);
        int waiterId = userInfo.get("userId").asInt();
        int restaurantId = userInfo.get("restaurantId").asInt();
        return ResponseWrapper.wrap(orderService.newOrder((Integer) body.get("tableId"), waiterId, restaurantId,
                (List<Map<String, Object>>) body.get("orderItems")));
    }

    @PutMapping("/checkout")
    public ResponseEntity<Object> checkout(@RequestBody Map<String, Integer> body) {
        return ResponseWrapper.wrap(orderService.checkout((Integer) body.get("orderId")));
    }

    @GetMapping("/ongoing")
    public ResponseEntity<Object> getOngoingOrders(@RequestHeader("Authorization") String token) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(orderService.getOngoingOrders(restaurantId));
    }

    @GetMapping("/range")
    public ResponseEntity<Object> getOrdersInTimeRange(@RequestHeader("Authorization") String token,
            @RequestBody Map<String, Integer> body) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(orderService.getOrdersInRange(restaurantId, body.get("from"), body.get("to")));
    }
}

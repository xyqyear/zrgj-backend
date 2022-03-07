package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.service.OrderItemService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

@RestController
@RequestMapping("/order_item")
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;

    @PostMapping("/uncompleted")
    public ResponseEntity<Object> getUncompletedOrderItems(@RequestHeader("Authorization") String token) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(orderItemService.getUncompletedOrderItems(restaurantId));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateOrderItem(@RequestBody OrderItem orderItem) {
        return ResponseWrapper.wrap(orderItemService.updateOrderItem(orderItem));
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteOrderItem(@RequestBody OrderItem orderItem) {
        return ResponseWrapper.wrap(orderItemService.deleteOrderItem(orderItem));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> insertOrderItem(@RequestBody OrderItem orderItem) {
        return ResponseWrapper.wrap(orderItemService.createOrderItem(orderItem));
    }

}

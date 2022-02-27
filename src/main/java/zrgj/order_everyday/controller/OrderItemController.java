package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(orderItem.getState());
        return ResponseWrapper.wrap(orderItemService.updateOrderItem(orderItem));
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteOrderItem(@RequestBody Integer orderItemId) {
        return ResponseWrapper.wrap(orderItemService.deleteOrderItem(orderItemId));
    }
}

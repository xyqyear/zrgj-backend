package zrgj.order_everyday.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zrgj.order_everyday.entity.Restaurant;
import zrgj.order_everyday.service.RestaurantService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/get")
    public ResponseEntity<Object> getRestaurantInfo(@RequestHeader("Authorization") String token) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(restaurantService.getRestaurantInfo(restaurantId));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateRestaurantInfo(@RequestHeader("Authorization") String token, @RequestBody Restaurant restaurant) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        restaurant.setId(restaurantId);
        return ResponseWrapper.wrap(restaurantService.updateRestaurantInfo(restaurant));
    }

    @PostMapping("/queue_status/get")
    public ResponseEntity<Object> getRestaurantQueueStatus(@RequestHeader("Authorization") String token) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(restaurantService.getRestaurantQueueStatus(restaurantId));
    }

    @PostMapping("/queue_status/update")
    public ResponseEntity<Object> updateRestaurantQueueStatus(@RequestHeader("Authorization") String token, @RequestBody JsonNode queueStatus) {
        int restaurantId = JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(restaurantService.updateRestaurantQueueStatus(restaurantId, queueStatus));
    }

}

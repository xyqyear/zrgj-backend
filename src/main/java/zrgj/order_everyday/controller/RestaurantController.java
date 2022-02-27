package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

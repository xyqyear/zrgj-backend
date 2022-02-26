package zrgj.order_everyday.controller;


import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zrgj.order_everyday.entity.Dish;
import zrgj.order_everyday.service.DishService;
import zrgj.order_everyday.util.JWTUtil;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @RequestMapping(method = RequestMethod.POST)
    @RequiresRoles("0")
    public ResponseEntity<Object> addNewDish(@RequestBody Dish dish,
                                             @RequestHeader("Authorization") String token) {
        dish.setRestaurantId(JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt());
        return ResponseWrapper.wrap(dishService.addNewDish(dish));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresRoles("0")
    public ResponseEntity<Object> deleteDish(@RequestBody Map<String, String> body,
                                             @RequestHeader("Authorization") String token) {
        return ResponseWrapper.wrap(dishService.deleteDish(body.get("id")));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @RequiresRoles("0")
    public ResponseEntity<Object> updateDish(@RequestBody Dish dish,
                                             @RequestHeader("Authorization") String token) {
        return ResponseWrapper.wrap(dishService.updateDish(dish));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getDishList(@RequestHeader("Authorization") String token) {
        Integer restaurantId=JWTUtil.getClaimsFromHeader(token).get("restaurantId").asInt();
        return ResponseWrapper.wrap(dishService.getDishList(restaurantId));
    }
}

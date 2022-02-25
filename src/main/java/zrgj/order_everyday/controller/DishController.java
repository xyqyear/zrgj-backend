package zrgj.order_everyday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.entity.Dish;
import zrgj.order_everyday.service.DishService;
import zrgj.order_everyday.util.ResponseWrapper;

import java.util.Map;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addNewDish(@RequestBody Dish dish) {
        return ResponseWrapper.wrap(dishService.addNewDish(dish));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteDish(@RequestBody Map<String, String> body) {
        return ResponseWrapper.wrap(dishService.deleteDish(body.get("id")));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> updateDish(@RequestBody Dish dish) {
        return ResponseWrapper.wrap(dishService.updateDish(dish));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<Object> getDishList(@RequestBody Map<String, String> body) {
        return ResponseWrapper.wrap(dishService.getDishList(body.get("restaurantId")));
    }
}

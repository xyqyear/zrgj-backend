package zrgj.order_everyday.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zrgj.order_everyday.service.DishService;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
}

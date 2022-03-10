package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Dish;
import zrgj.order_everyday.mapper.DishMapper;
import zrgj.order_everyday.mapper.RestaurantMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    private SimpMessagingTemplate template;

    public ResultMap addNewDish(Dish dish) {
        Dish oldDish = dishMapper.getDishByName(dish);
        if (oldDish != null) {
            return ResultMap.failure("this dish already exists");
        }
        int result = dishMapper.addNewDish(dish);
        this.template.convertAndSend("/dish/" + dish.getRestaurantId(), dishMapper.getDishList(dish.getRestaurantId()));
        return ResultMap.success(result);
    }

    public ResultMap deleteDish(String id, int restaurantId) {
        if (dishMapper.deleteDish(id) == 0) {
            return ResultMap.failure("this dish doesn't exist");
        }
        this.template.convertAndSend("/dish/" + restaurantId, dishMapper.getDishList(restaurantId));
        return ResultMap.success(null);
    }

    public ResultMap updateDish(Dish dish, int restaurantId) {
        if (dishMapper.updateDish(dish) == 0) {
            return ResultMap.failure("this dish doesn't exist");
        }
        this.template.convertAndSend("/dish/" + restaurantId, dishMapper.getDishList(restaurantId));
        return ResultMap.success(null);
    }

    public ResultMap getDishList(Integer restaurantId) {
        return ResultMap.success(dishMapper.getDishList(restaurantId));
    }
}

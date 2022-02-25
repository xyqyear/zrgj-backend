package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public ResultMap addNewDish(Dish dish) {
        Dish oldDish = dishMapper.getDishByName(dish);
        if (oldDish != null) {
            return ResultMap.failure("this dish already exists");
        }
        return ResultMap.success(dishMapper.addNewDish(dish));
    }

    public ResultMap deleteDish(String id) {
        if (dishMapper.deleteDish(id) == 0){
            return ResultMap.failure("this dish doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap updateDish(Dish dish) {
        if (dishMapper.updateDish(dish) == 0){
            return ResultMap.failure("this dish doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap getDishList(String restaurantId) {
//        Restaurant restaurant =restaurantMapper.getRestaurantById(restaurantId);
//        if (restaurant == null) {
//            return ResultMap.failure("this restaurant doesn't exist");
//        }
        return ResultMap.success(dishMapper.getDishList(restaurantId));
    }
}

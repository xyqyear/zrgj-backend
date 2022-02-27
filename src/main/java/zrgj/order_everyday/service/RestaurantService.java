package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.Restaurant;
import zrgj.order_everyday.mapper.RestaurantMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class RestaurantService {
    
    @Autowired
    RestaurantMapper restaurantMapper;

    public ResultMap getRestaurantInfo(Integer restaurantId) {
        return ResultMap.success(restaurantMapper.getRestaurantById(restaurantId));
    }

    public ResultMap updateRestaurantInfo(Restaurant restaurant) {
        restaurantMapper.updateRestaurant(restaurant);
        return ResultMap.success(null);
    }
}

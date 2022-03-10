package zrgj.order_everyday.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Restaurant;
import zrgj.order_everyday.mapper.RestaurantMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class RestaurantService {
    
    @Autowired
    RestaurantMapper restaurantMapper;
    
    @Autowired
    private SimpMessagingTemplate template;

    public ResultMap getRestaurantInfo(Integer restaurantId) {
        return ResultMap.success(restaurantMapper.getRestaurantById(restaurantId));
    }

    public ResultMap updateRestaurantInfo(Restaurant restaurant) {
        restaurantMapper.updateRestaurant(restaurant);
        return ResultMap.success(null);
    }

    public ResultMap getRestaurantQueueStatus(Integer restaurantId) {
        return ResultMap.success(restaurantMapper.getRestaurantById(restaurantId).getQueueStatus());
    }

    public ResultMap updateRestaurantQueueStatus(Integer restaurantId, JsonNode queueStatus) {
        Restaurant restaurant = restaurantMapper.getRestaurantById(restaurantId);
        restaurant.setQueueStatus(queueStatus);
        restaurantMapper.updateRestaurantQueueStatus(restaurant);
        this.template.convertAndSend("/queue/" + restaurantId, queueStatus);
        return ResultMap.success(null);
    }
}

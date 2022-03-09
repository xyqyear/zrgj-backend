package zrgj.order_everyday.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Restaurant;

@Mapper
@Repository
public interface RestaurantMapper {
    Restaurant getRestaurantById(Integer id);

    Integer updateRestaurant(Restaurant restaurant);

    Integer updateRestaurantQueueStatus(Restaurant restaurant);
}

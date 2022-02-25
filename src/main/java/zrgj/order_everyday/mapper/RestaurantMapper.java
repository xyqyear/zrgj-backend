package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Restaurant;

@Mapper
@Repository
public interface RestaurantMapper {
    Restaurant getRestaurantById(String id);
}

package zrgj.order_everyday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import zrgj.order_everyday.entity.Order;

@Mapper
@Repository
public interface OrderMapper {
    @Insert("INSERT INTO `order` (`table_id`, `create_time`, `state`, `waiter_id`, `restaurant_id`) VALUES (#{tableId}, #{createTime}, #{state}, #{waiterId}, #{restaurantId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void createOrder(Order order);

    // get order by id
    @Select("SELECT * FROM `order` WHERE `id` = #{orderId}")
    Order getOrderById(Integer orderId);

    // select state is 1 or 2 and restaurant_id is restaurantId
    @Select("SELECT * FROM `order` WHERE `state` IN (1, 2) AND `restaurant_id` = #{restaurantId}")
    List<Order> getOngoingOrders(Integer restaurantId);

    //update with state and payTime
    @Update("UPDATE `order` SET `state` = #{state}, `pay_time` = #{payTime} WHERE `id` = #{id}")
    void updateOrder(Integer id, Integer state, Integer payTime);

    // select orders in a range
    @Select("SELECT * FROM `order` WHERE `create_time` >= #{from} AND `create_time` <= #{to} AND `restaurant_id` = #{restaurantId}")
    List<Order> getOrdersInRange(Integer restaurantId,Integer from, Integer to);
}

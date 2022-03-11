package zrgj.order_everyday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import zrgj.order_everyday.entity.Order;

@Mapper
@Repository
public interface OrderMapper {
    @Insert("INSERT INTO `order` (`table_id`, `create_time`, `state`, `waiter_id`, `restaurant_id`) VALUES (#{tableId}, #{createTime}, #{state}, #{waiterId}, #{restaurantId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void createOrder(Order order);

    Order getOrderById(Integer orderId);

    List<Order> getOngoingOrders(Integer restaurantId);

    //update with state and payTime
    @Update("UPDATE `order` SET `state` = #{state}, `pay_time` = #{payTime}, `actually_paid`= #{actuallyPaid} WHERE `id` = #{id}")
    void updateOrder(Order order);

    List<Order> getOrdersInRange(Integer restaurantId,Integer from, Integer to);
}

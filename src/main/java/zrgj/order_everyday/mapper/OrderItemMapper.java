package zrgj.order_everyday.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import zrgj.order_everyday.entity.OrderItem;

@Mapper
@Repository
public interface OrderItemMapper {
    // create order item
    @Insert("INSERT INTO `order_item` (`dish_id`, `state`, `amount`, `note`, `order_id`) VALUES (#{dishId}, #{state}, #{amount}, #{note}, #{orderId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    Integer createOrderItem(OrderItem orderItem);

    // get order item by id
    @Select("SELECT * FROM `order_item` WHERE `id` = #{id}")
    OrderItem getOrderItemById(Integer id);

    // get order items with state 1 and belong to an order from restaurandId
    @Select("SELECT * FROM `order_item` WHERE `state` = 1 AND `order_id` IN (SELECT `id` FROM `order` WHERE `restaurant_id` = #{restaurantId})")
    List<OrderItem> getUncompletedOrderItems(Integer restaurantId);

    // get order items by order id
    @Select("SELECT * FROM `order_item` WHERE `order_id` = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    // set order item state to -1 if it's state isn't 0 by order id
    @Update("UPDATE `order_item` SET `state` = -1 WHERE `order_id` = #{orderId} AND `state` != 0")
    void cancelOrderItemByOrderId(Integer orderId);

    // update order item with state, dishId, amount and note
    @Update("UPDATE `order_item` SET `state` = #{state}, `dish_id` = #{dishId}, `amount` = #{amount}, `note` = #{note} WHERE `id` = #{id}")
    void updateOrderItem(OrderItem orderItem);

    // set state to -1
    @Update("UPDATE `order_item` SET `state` = -1 WHERE `id` = #{id}")
    Integer deleteOrderItem(Integer id);
}

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
    Integer createOrderItem(Integer dishId, Integer state, Integer amount, String note, Integer orderId);

    // get order item by id
    @Select("SELECT * FROM `order_item` WHERE `id` = #{id}")
    OrderItem getOrderItemById(Integer id);

    // get order items the state of which is 1
    @Select("SELECT * FROM `order_item` WHERE `state` = 1")
    List<OrderItem> getUncompletedOrderItems();

    // get order items by order id
    @Select("SELECT * FROM `order_item` WHERE `order_id` = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    // update order item with state, dishId, amount and note
    @Update("UPDATE `order_item` SET `state` = #{state}, `dish_id` = #{dishId}, `amount` = #{amount}, `note` = #{note} WHERE `id` = #{id}")
    void updateOrderItem(Integer id, Integer state, Integer dishId, Integer amount, String note);

    // set state to -1
    @Update("UPDATE `order_item` SET `state` = -1 WHERE `id` = #{id}")
    void deleteOrderItem(Integer id);
}

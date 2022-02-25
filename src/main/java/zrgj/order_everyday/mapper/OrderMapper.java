package zrgj.order_everyday.mapper;

import java.sql.Timestamp;
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
    Integer createOrder(Integer tableId, Timestamp createTime, Integer state, Integer waiterId, Integer restaurantId);

    // get order by id union get total price by id
    @Select("SELECT * FROM `order` WHERE `id` = #{orderId}")
    Order getOrderById(Integer orderId);

    // select state is 1 or 2 and ignore table_id
    @Select("SELECT * FROM `order` WHERE `state` = 1 OR `state` = 2")
    List<Order> getOngoingOrders();

    //update with state and payTime
    @Update("UPDATE `order` SET `state` = #{state}, `pay_time` = #{payTime} WHERE `id` = #{id}")
    void updateOrder(Integer id, Integer state, Timestamp payTime);

    // select orders in a range
    @Select("SELECT * FROM `order` WHERE `create_time` >= #{start} AND `create_time` <= #{end}")
    List<Order> getOrdersInRange(Timestamp start, Timestamp end);
}

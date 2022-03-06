package zrgj.order_everyday.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.mapper.DishMapper;
import zrgj.order_everyday.mapper.OrderItemMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    DishMapper dishMapper;

    // get uncompleted order items
    public ResultMap getUncompletedOrderItems(Integer restaurantId) {
        List<OrderItem> orderItems = orderItemMapper.getUncompletedOrderItems(restaurantId);
        return ResultMap.success(orderItems);
    }

    // update order item
    public ResultMap updateOrderItem(OrderItem orderItem) {
        orderItemMapper.updateOrderItem(orderItem);
        return ResultMap.success(null);
    }

    // delete order item
    public ResultMap deleteOrderItem(OrderItem orderItem) {
        int deletedNum = orderItemMapper.deleteOrderItem(orderItem.getId());
        if (deletedNum < 1) {
            return ResultMap.failure("orderItem " + orderItem.getId() + " not found or already deleted");
        }
        return ResultMap.success(null);
    }

    // create order item
    public ResultMap createOrderItem(OrderItem orderItem) {
        if (orderItem.getAmount() < 1) {
            return ResultMap.failure("amount must be positive integer");
        }
        orderItem.setState(1);
        if (dishMapper.getDishById(orderItem.getDishId()) == null) {
            return ResultMap.failure("invalid dish in this orderItem");
        }

        try {
            orderItemMapper.createOrderItem(orderItem);
        } catch (Exception e) {
            return ResultMap.failure("invalid order in this orderItem");
        }

        return ResultMap.success((null));
    }

}

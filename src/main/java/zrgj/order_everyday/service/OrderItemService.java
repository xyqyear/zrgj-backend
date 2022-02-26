package zrgj.order_everyday.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.mapper.OrderItemMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

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
    public ResultMap deleteOrderItem(Integer orderItemId) {
        orderItemMapper.deleteOrderItem(orderItemId);
        return ResultMap.success(null);
    }
}

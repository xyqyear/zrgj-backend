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
    public ResultMap getUncompletedOrderItems() {
        List<OrderItem> orderItems = orderItemMapper.getUncompletedOrderItems();
        return ResultMap.success(orderItems);
    }

    // update order item
    public ResultMap updateOrderItem(OrderItem orderItem) {
        // update by id, state, dishId, amount and note
        orderItemMapper.updateOrderItem(orderItem.getId(), orderItem.getState(), orderItem.getDishId(),
                orderItem.getAmount(), orderItem.getNote());
        return ResultMap.success(null);
    }

    // delete order item
    public ResultMap deleteOrderItem(Integer orderItemId) {
        orderItemMapper.deleteOrderItem(orderItemId);
        return ResultMap.success(null);
    }
}

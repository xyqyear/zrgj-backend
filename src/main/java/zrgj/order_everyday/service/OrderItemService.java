package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Dish;
import zrgj.order_everyday.entity.Notification;
import zrgj.order_everyday.entity.Order;
import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.mapper.DishMapper;
import zrgj.order_everyday.mapper.OrderItemMapper;
import zrgj.order_everyday.mapper.OrderMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    NotificationService notificationService;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    OrderMapper orderMapper;

    // get uncompleted order items
    public ResultMap getUncompletedOrderItems(Integer restaurantId) {
        List<OrderItem> orderItems = orderItemMapper.getUncompletedOrderItems(restaurantId);
        return ResultMap.success(orderItems);
    }

    // update order item
    public ResultMap updateOrderItem(OrderItem orderItem, Integer userId) {
        OrderItem oi = orderItemMapper.getOrderItemById(orderItem.getId());
        // 生成订单项状态修改给对应的群体
        // 1 =》 2 开始烹饪
        if (oi.getState() == 1 && orderItem.getState() == 2) {
            orderItem.setCookId(userId);
        }
        // 2 =》 0 烹饪完成
        if (oi.getState() == 2 && orderItem.getState() == 0) {
            Order order = orderMapper.getOrderById(oi.getOrderId());
            Dish dish = dishMapper.getDishById(oi.getDishId());
            Notification notification = new Notification();
            notification.setSenderId(userId);
            notification.setRestaurantId(order.getRestaurantId());
            notification.setSticked(false);
            notification.setReceiverType(1);
            notification.setTitle("请送餐到桌");
            notification.setContent(order.getTableId().toString() + "号餐桌有新的菜品(" +
                    dish.getName() + ")已烹饪完成，请尽快送餐到桌！！");
            notificationService.addNotification(notification);
        }
        // 1 => -1 订单项取消
        if (oi.getState() == 1 && orderItem.getState() == -1) {
            Order order = orderMapper.getOrderById(orderItem.getOrderId());
            Dish dish = dishMapper.getDishById(orderItem.getDishId());
            Notification notification = new Notification();
            notification.setSenderId(userId);
            notification.setRestaurantId(order.getRestaurantId());
            notification.setSticked(false);
            notification.setReceiverType(2);
            notification.setTitle("订单项取消");
            notification.setContent(order.getTableId().toString() + "号餐桌取消了订单项(" +
                    dish.getName() + " " + orderItem.getAmount() + " 份)，请注意查看");
            notificationService.addNotification(notification);
        }
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

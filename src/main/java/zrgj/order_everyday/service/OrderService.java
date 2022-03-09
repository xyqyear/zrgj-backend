package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.Order;
import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.entity.Restaurant;
import zrgj.order_everyday.mapper.DishMapper;
import zrgj.order_everyday.mapper.OrderItemMapper;
import zrgj.order_everyday.mapper.OrderMapper;
import zrgj.order_everyday.mapper.RestaurantMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    private SimpMessagingTemplate template;

    public ResultMap newOrder(Order order, Integer waiterId, Integer restaurantId) {
        Restaurant restaurant = restaurantMapper.getRestaurantById(restaurantId);
        if (order.getTableId() > restaurant.getTableNum()) {
            return ResultMap.failure("invalid table id");
        }
        order.setWaiterId(waiterId);
        order.setRestaurantId(restaurantId);
        order.setCreateTime((int) (System.currentTimeMillis() / 1000));
        order.setState(1);
        orderMapper.createOrder(order);
        int i = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            if ((Integer) orderItem.getAmount() < 1) {
                return ResultMap.failure("amount must be positive integer");
            }
            orderItem.setState(1);
            orderItem.setOrderId(order.getId());
            try {
                orderItemMapper.createOrderItem(orderItem);
            } catch (Exception e) {
                return ResultMap.failure("invalid dish in orderItems[" + i + "]");
            }
            i++;
        }
        this.template.convertAndSend("/orders/" + restaurantId, orderMapper.getOngoingOrders(restaurantId));
        return ResultMap.success(null);
    }

    public ResultMap checkout(Integer orderId) {
        Order order = orderMapper.getOrderById(orderId);
        if (order.getState() == 0) {
            return ResultMap.failure("order " + orderId + " is already checked out");
        }
        order.setState(0);
        order.setPayTime((int) (System.currentTimeMillis() / 1000));
        orderItemMapper.cancelOrderItemByOrderId(orderId);
        this.updateOrder(order);
        this.template.convertAndSend("/orders/" + order.getRestaurantId(), orderMapper.getOngoingOrders(order.getRestaurantId()));
        return ResultMap.success(null);
    }

    public ResultMap updateOrder(Order order) {
        orderMapper.updateOrder(order);
        this.template.convertAndSend("/orders/" + order.getRestaurantId(), orderMapper.getOngoingOrders(order.getRestaurantId()));
        return ResultMap.success(null);
    }

    public ResultMap getOngoingOrders(Integer restaurantId) {
        return ResultMap.success(orderMapper.getOngoingOrders(restaurantId));
    }

    public ResultMap getOrdersInRange(Integer restaurantId, Integer from, Integer to) {
        return ResultMap.success(orderMapper.getOrdersInRange(restaurantId, from, to));
    }
}

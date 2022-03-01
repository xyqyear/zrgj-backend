package zrgj.order_everyday.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.Dish;
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

    public ResultMap newOrder(Integer tableId, Integer waiterId, Integer restaurantId,
            List<Map<String, Object>> orderItems) {
        Order order = new Order();
        Restaurant restaurant = restaurantMapper.getRestaurantById(restaurantId);
        if (tableId > restaurant.getTableNum()) {
            return ResultMap.failure("invalid table id");
        }
        order.setTableId(tableId);
        order.setWaiterId(waiterId);
        order.setRestaurantId(restaurantId);
        order.setCreateTime((int) (System.currentTimeMillis() / 1000));
        order.setState(1);
        orderMapper.createOrder(order);
        Integer i = 0;
        for (Map<String, Object> orderItem : orderItems) {
            OrderItem orderItemEntity = new OrderItem();
            if ((Integer) orderItem.get("amount") < 1) {
                return ResultMap.failure("amount must be positive integer");
            }
            orderItemEntity.setDishId((Integer) orderItem.get("dishId"));
            orderItemEntity.setAmount((Integer) orderItem.get("amount"));
            orderItemEntity.setNote((String) orderItem.get("note"));
            orderItemEntity.setState(1);
            orderItemEntity.setOrderId(order.getId());
            try{
                orderItemMapper.createOrderItem(orderItemEntity);
            } catch (Exception e) {
                return ResultMap.failure("invalid dish in orderItems[" + i + "]");
            }
            i++;
        }

        return ResultMap.success(null);
    }

    public ResultMap checkout(Integer orderId) {
        Order order = orderMapper.getOrderById(orderId);
        order.setState(0);
        order.setPayTime((int) (System.currentTimeMillis() / 1000));
        List<OrderItem> orderItems = orderItemMapper.getUncompletedOrderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            orderItem.setState(-1);
            orderItemMapper.updateOrderItem(orderItem);
        }
        this.updateOrder(order);
        return ResultMap.success(null);
    }

    public ResultMap updateOrder(Order order) {
        orderMapper.updateOrder(order);
        return ResultMap.success(null);
    }

    public ResultMap getOngoingOrders(Integer restaurantId) {
        List<Map<String, Object>> orders = new ArrayList<>();
        List<Order> ongoingOrders = orderMapper.getOngoingOrders(restaurantId);
        for (Order order : ongoingOrders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("tableId", order.getTableId());
            orderMap.put("state", order.getState());
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            orderMap.put("waiterId", order.getWaiterId());
            List<OrderItem> orderItems = orderItemMapper.getOrderItemsByOrderId(order.getId());
            int totalPrice = 0;
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getState() != -1) {
                    Dish dish = dishMapper.getDishById(orderItem.getDishId());
                    totalPrice += orderItem.getAmount() * dish.getPrice();
                }
            }
            orderMap.put("orderItems", orderItems);
            orderMap.put("totalPrice", totalPrice);
            orders.add(orderMap);
        }
        return ResultMap.success(orders);
    }

    public ResultMap getOrdersInRange(Integer restaurantId, Integer start, Integer end) {
        List<Map<String, Object>> orders = new ArrayList<>();
        List<Order> ordersInRange = orderMapper.getOrdersInRange(restaurantId, start, end);
        for (Order order : ordersInRange) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("tableId", order.getTableId());
            orderMap.put("state", order.getState());
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
            orderMap.put("waiterId", order.getWaiterId());
            List<OrderItem> orderItems = orderItemMapper.getOrderItemsByOrderId(order.getId());
            int totalPrice = 0;
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getState() == 0) {
                    Dish dish = dishMapper.getDishById(orderItem.getDishId());
                    totalPrice += orderItem.getAmount() * dish.getPrice();
                }
            }
            orderMap.put("orderItems", orderItems);
            orderMap.put("totalPrice", totalPrice);
            orders.add(orderMap);
        }
        return ResultMap.success(orders);
    }
}

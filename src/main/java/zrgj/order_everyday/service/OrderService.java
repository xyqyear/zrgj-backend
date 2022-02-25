package zrgj.order_everyday.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.Dish;
import zrgj.order_everyday.entity.Order;
import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.mapper.DishMapper;
import zrgj.order_everyday.mapper.OrderItemMapper;
import zrgj.order_everyday.mapper.OrderMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    DishMapper dishMapper;

    public ResultMap newOrder(Integer tableId, List<Map<String, Object>> orderItems) {
        Order order = new Order();
        order.setTableId(tableId);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setState(1);
        orderMapper.createOrder(order);
        for (Map<String, Object> orderItem : orderItems) {
            OrderItem orderItemEntity = new OrderItem();
            orderItemEntity.setDishId((Integer) orderItem.get("dishId"));
            orderItemEntity.setAmount((Integer) orderItem.get("amount"));
            orderItemEntity.setNote((String) orderItem.get("note"));
            orderItemEntity.setState(1);
            orderItemEntity.setOrderId(order.getId());
            orderItemMapper.createOrderItem(orderItemEntity);
        }

        return ResultMap.success(null);
    }

    public ResultMap checkout(Integer orderId) {
        Order order = orderMapper.getOrderById(orderId);
        order.setState(0);
        order.setPayTime(new Timestamp(System.currentTimeMillis()));
        // get order items by order id
        List<OrderItem> orderItems = orderItemMapper.getUncompletedOrderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            orderItem.setState(-1);
            orderItemMapper.updateOrderItem(orderItem);
        }
        this.updateOrder(order);
        return ResultMap.success(null);
    }

    public ResultMap updateOrder(Order order) {
        orderMapper.updateOrder(order.getId(), order.getState(), order.getPayTime());
        return ResultMap.success(null);
    }

    public ResultMap getOngoingOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();
        List<Order> ongoingOrders = orderMapper.getOngoingOrders();
        for (Order order : ongoingOrders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("tableId", order.getTableId());
            orderMap.put("state", order.getState());
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
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

    public ResultMap getOrdersInRange(Integer start, Integer end) {
        Timestamp startTimestamp = new Timestamp(start);
        Timestamp endTimestamp = new Timestamp(end);
        List<Map<String, Object>> orders = new ArrayList<>();
        List<Order> ordersInRange = orderMapper.getOrdersInRange(startTimestamp, endTimestamp);
        for (Order order : ordersInRange) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("tableId", order.getTableId());
            orderMap.put("state", order.getState());
            orderMap.put("createTime", order.getCreateTime());
            orderMap.put("payTime", order.getPayTime());
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

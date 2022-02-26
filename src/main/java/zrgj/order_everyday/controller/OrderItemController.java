package zrgj.order_everyday.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zrgj.order_everyday.entity.OrderItem;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.OrderItemService;

@RestController
@RequestMapping("/order_item")
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/uncompleted")
    public ResultMap getUncompletedOrderItems() {
        return ResultMap.success(orderItemService.getUncompletedOrderItems());
    }

    @PutMapping("/update")
    public ResultMap updateOrderItem(OrderItem orderItem) {
        orderItemService.updateOrderItem(orderItem);
        return ResultMap.success(null);
    }

    @DeleteMapping("/delete")
    public ResultMap deleteOrderItem(Integer orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return ResultMap.success(null);
    }
}

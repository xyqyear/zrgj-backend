package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer tableId;
    private Integer createTime;
    private Integer payTime;
    private Integer state;
    private Integer waiterId;
    private Integer restaurantId;
    private List<OrderItem> orderItems;
}

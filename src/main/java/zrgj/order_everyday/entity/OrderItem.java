package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer id;
    private Dish dish;
    private Integer state;
    private Integer amount;
    private String note;
    private Integer cookId;
    private Integer orderId;
}

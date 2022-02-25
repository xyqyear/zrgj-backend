package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Integer tableId;
    private Timestamp createTime;
    private Timestamp payTime;
    private Integer state;
    private Integer waiterId;
    private Integer restaurantId;
}

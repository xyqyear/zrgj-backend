package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer id;
    private Integer dishId;
    private Integer state;
    private Integer amount;
    private String note;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer cookId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer orderId;
}

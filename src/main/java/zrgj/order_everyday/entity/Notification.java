package zrgj.order_everyday.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Integer id;
    private Integer createTime;
    private String title;
    private String content;
    private Integer senderId;
    private Integer receiverType;
    private Integer restaurantId;
    private Boolean sticked;
    private JsonNode confirmation;
}

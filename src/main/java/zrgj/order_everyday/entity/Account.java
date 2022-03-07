package zrgj.order_everyday.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String username;
    private Integer position;
    private String idCard;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Integer restaurantId;
    private String avatarUrl;
    private String telephone;
}

package zrgj.order_everyday.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private String telephone;
    private Integer tableNum;
    private JsonNode tableInfo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private JsonNode queueStatus;
}

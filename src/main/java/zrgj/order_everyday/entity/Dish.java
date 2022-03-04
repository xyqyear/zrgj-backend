package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Integer id;
    private String name;
    private Float price;
    private String imageUrl;
    private String category;
    private JsonNode flavour;
    private Integer restaurantId;
}

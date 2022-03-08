package zrgj.order_everyday.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Vip {
    private Integer id;
    private String name;
    private String telephone;
    private Integer score;
}

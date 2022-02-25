package zrgj.order_everyday.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap {
    private Boolean code;

    private String reason;
    
    private Object data;

}

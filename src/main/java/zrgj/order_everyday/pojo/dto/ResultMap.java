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
    private Boolean success;

    private String reason;
    
    private Object data;

    public static ResultMap failure(String reason) {
        return ResultMap.builder().success(false).reason(reason).build();
    }

    public static ResultMap success(Object data){
        return ResultMap.builder().success(true).data(data).build();
    }
}

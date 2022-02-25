package zrgj.order_everyday.util;

import org.springframework.http.ResponseEntity;
import zrgj.order_everyday.pojo.dto.ResultMap;

public class ResponseWrapper {
    public static ResponseEntity<Object> wrap(ResultMap result){
        int statusCode;
        if (result.getSuccess()) {
            statusCode = 200;
        } else {
            statusCode = 400;
        }
        return ResponseEntity.status(statusCode).body(result);
    }
}

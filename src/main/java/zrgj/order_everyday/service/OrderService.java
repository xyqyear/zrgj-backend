package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.mapper.OrderMapper;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;
}

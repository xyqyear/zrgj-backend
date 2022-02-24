package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Account;

@Mapper
@Repository
public interface AccountMapper {
    Account getAccountByUsername(String username);

}

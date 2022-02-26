package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Account;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    Account getAccountByUsername(String username);

    Integer addNewAccount(Account account);

    Integer updateAccount(Account account);

    Integer changePassword(Account account);

    Integer deleteAccount(String username);

    List<Account> getAccountList(Integer restaurantId);
}

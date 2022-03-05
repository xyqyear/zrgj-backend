package zrgj.order_everyday.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zrgj.order_everyday.entity.Account;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    Account getAccountById(Integer id);

    Integer addNewAccount(Account account);

    Integer updateAccount(Account account);

    Integer changePassword(Account account);

    Integer deleteAccount(Integer id);

    List<Integer> getIdByPosition(Integer restaurantId,Integer position);

    List<Account> getAccountList(Integer restaurantId);
}

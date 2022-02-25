package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.mapper.AccountMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.util.Encryption;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public ResultMap authenticate(String username, String password){

        Account user = accountMapper.getAccountByUsername(username);
        if (user == null){
            return ResultMap.failure("invalid username");
        }
        String encryptedPassword = Encryption.getSHA256(password);
        if (! encryptedPassword.equals(user.getPassword())){
            return ResultMap.failure("wrong password");
        }
        user.setPassword("就不告诉你");
        return ResultMap.success(user);
    }

    public ResultMap addNewAccount(Account account) {
        Account user = accountMapper.getAccountByUsername(account.getUsername());
        if (user != null){
            return ResultMap.failure("username already exists");
        }
        account.setPassword(Encryption.getSHA256(account.getPassword()));
        accountMapper.addNewAccount(account);
        return ResultMap.success(null);
    }

    public ResultMap getAccountInfo(String username) {
        Account user = accountMapper.getAccountByUsername(username);
        if (user == null){
            return ResultMap.failure("invalid username");
        }
        user.setPassword("就不告诉你");
        return ResultMap.success(user);
    }


    public ResultMap updateAccount(Account account) {
        accountMapper.updateAccount(account);
        return ResultMap.success(null);
    }

    public ResultMap changePassword(Account account) {
        account.setPassword(Encryption.getSHA256(account.getPassword()));
        accountMapper.changePassword(account);
        return ResultMap.success(null);
    }

    public ResultMap deleteAccount(String username) {
        int deleteNum = accountMapper.deleteAccount(username);
        if (deleteNum == 0) {
            return ResultMap.failure("username doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap getAccountList(String restaurantId) {
        List<Account> employees = accountMapper.getAccountList(restaurantId);
        return ResultMap.success(employees);
    }
}

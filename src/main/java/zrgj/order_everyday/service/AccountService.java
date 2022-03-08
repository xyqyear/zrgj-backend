package zrgj.order_everyday.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zrgj.order_everyday.entity.Account;
import zrgj.order_everyday.mapper.AccountMapper;
import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.util.Encryption;
import zrgj.order_everyday.util.JWTUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    public ResultMap authenticate(Integer id, String password){

        Account user = accountMapper.getAccountById(id);
        if (user == null){
            return ResultMap.failure("invalid id");
        }
        String encryptedPassword = Encryption.getSHA256(password);
        if (! encryptedPassword.equals(user.getPassword())){
            return ResultMap.failure("wrong password");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", JWTUtil.createToken(user));
        map.put("account", user);
        return ResultMap.success(map);
    }

    public ResultMap addNewAccount(Account account) {
        account.setPassword(Encryption.getSHA256(account.getPassword()));
        accountMapper.addNewAccount(account);
        return ResultMap.success(null);
    }

    public ResultMap getAccountInfo(Integer id) {
        Account user = accountMapper.getAccountById(id);
        if (user == null){
            return ResultMap.failure("invalid user id");
        }
        user.setPassword("就不告诉你");
        return ResultMap.success(user);
    }

    public ResultMap updateAccount(Account account) {
        Account user = accountMapper.getAccountById(account.getId());
        if (user == null) {
            return ResultMap.failure("invalid id");
        }
        if (account.getPassword() == null || account.getPassword().equals("")) {
            account.setPassword(user.getPassword());
        } else {
            account.setPassword(Encryption.getSHA256(account.getPassword()));
        }
        accountMapper.updateAccount(account);
        return ResultMap.success(null);
    }

    public ResultMap changePassword(Account account) {
        account.setPassword(Encryption.getSHA256(account.getPassword()));
        accountMapper.changePassword(account);
        return ResultMap.success(null);
    }

    public ResultMap deleteAccount(Integer id) {
        int deleteNum = accountMapper.deleteAccount(id);
        if (deleteNum == 0) {
            return ResultMap.failure("user id doesn't exist");
        }
        return ResultMap.success(null);
    }

    public ResultMap getAccountList(Integer restaurantId) {
        List<Account> employees = accountMapper.getAccountList(restaurantId);
        return ResultMap.success(employees);
    }
}

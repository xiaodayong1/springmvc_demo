package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;
import com.lagou.edu.factory.BeanFactory;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.TransactionManager;

/**
 * @author 应癫
 */
public class TransferServiceImpl implements TransferService {

    //private AccountDao accountDao = new JdbcAccountDaoImpl();

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao){
        this.accountDao=accountDao;
    }

    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        try {
            //开启事务关闭自动提交
            //ConnectionUtils.getInstance().getCurrentThreadConn().setAutoCommit(false);

            TransactionManager.getInstance().beginTransaction();

            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            int c=1/0;
            accountDao.updateAccountByCardNo(from);

            //提交事务
            TransactionManager.getInstance().commit();

        }catch (Exception e) {

            e.printStackTrace();

            //回滚事务
            //ConnectionUtils.getInstance().getCurrentThreadConn().rollback();
            TransactionManager.getInstance().rollback();
            throw new RuntimeException("事务控制回滚报错");
        }

    }
}

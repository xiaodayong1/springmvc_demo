package com.lagou.edu.factory;

import com.lagou.edu.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {

    public Object target;

    public InvocationHandlerImpl(Object target){
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            TransactionManager.getInstance().beginTransaction();
            result = method.invoke(target,args);
            TransactionManager.getInstance().commit();
        }catch (Exception e) {
            e.printStackTrace();
            TransactionManager.getInstance().rollback();
            throw e;
        }

        return result;
    }
}

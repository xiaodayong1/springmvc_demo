package com.lagou.edu.factory;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private ProxyFactory(){

    }

    private static ProxyFactory instance = new ProxyFactory();

    public static ProxyFactory getInstance() {
        return instance;
    }


    public Object getJDKProxy(final Object object){
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class<?>[] interfaces = object.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces,new InvocationHandlerImpl(object));
    }
}

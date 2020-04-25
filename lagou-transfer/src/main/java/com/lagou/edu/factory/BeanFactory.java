package com.lagou.edu.factory;
import java.lang.reflect.InvocationTargetException;
import	java.lang.reflect.Method;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import	java.util.HashMap;

import java.util.List;
import java.util.Map;

public class BeanFactory {

    private static Map<String,Object> Objectmap=new HashMap<String, Object> ();

    static {
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> list = rootElement.selectNodes("//bean");
            for (Element element : list) {
                String id=element.attributeValue("id");
                String clazz=element.attributeValue("class");
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();
                Objectmap.put(id,o);
            }

            List<Element> propertys = rootElement.selectNodes("//property");
            for (Element element:propertys ){  // <property name="AccountDao" ref="accountDao"></property>
                String name=element.attributeValue("name");
                String ref=element.attributeValue("ref");
                String parentId = element.getParent().attributeValue("id");
                Object parentObject= Objectmap.get(parentId);

                Method[] methods = parentObject.getClass().getMethods();
                for (Method method : methods){
                    if (method.getName().equalsIgnoreCase("set"+name)){
                       method.invoke(parentObject,Objectmap.get(ref));
                    }
                }
                Objectmap.put(parentId,parentObject);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String id){
        return Objectmap.get(id);
    }
}

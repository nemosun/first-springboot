package com.example.firstspringboot.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.firstspringboot.common.bean.CovertDemo;
import com.example.firstspringboot.common.demo.Annotation.ConvertAnnotation;
import com.example.firstspringboot.common.demo.Annotation.ExceptioFiledAannotation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: sunwenwu
 * @Date: 2019/7/22 16：14
 * @Description:
 */
public class NullFiledConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(NullFiledConvertUtil.class);

    public static <T> void convert(T t){
        Field[] fields = t.getClass().getDeclaredFields();

        for (Field field:fields) {
            ExceptioFiledAannotation annotation = field.getAnnotation(ExceptioFiledAannotation.class);
            if (annotation != null) {

                field.setAccessible(true);

                if (String.class.isAssignableFrom(field.getType())) {
                    try {
                        Object o = field.get(t);
                        if (o == null || StringUtils.isEmpty((String)o)) {
                            field.set(t,"-1");
                        }
                    } catch (Exception e) {
                        logger.error("数据转换异常",e);
                    }
                }
            }
        }
    }

   /* public static <T> Map<String,String> convertObj2Map(T t){
        Field[] fields = t.getClass().getDeclaredFields();

        Map<String,String> paramMap = new HashMap<>();
        for (Field field:fields) {
            ConvertAnnotation annotation = field.getAnnotation(ConvertAnnotation.class);
            if (annotation != null) {

                field.setAccessible(true);

                if (String.class.isAssignableFrom(field.getType())) {
                    try {
                        Object o = field.get(t);
                        if (o == null || StringUtils.isEmpty((String)o)) {
                            continue;
                        }
                        paramMap.put(annotation.mapKey(),(String)o);
                    } catch (Exception e) {
                        logger.error("数据转换异常",e);
                    }
                } else if (Long.class.isAssignableFrom(field.getType())) {
                    try {
                        Object o = field.get(t);
                        if (o == null) {
                            continue;
                        }

                        paramMap.put(annotation.mapKey(),AmountUtils.changeF2Y((Long)o));
                    } catch (Exception e) {
                        logger.error("数据转换异常",e);
                    }
                }
            }
        }
        return paramMap;
    }*/

    public static <T> Map<String,String> convertObj2Map(T t){
        return convertObj2MapWithSon(t,false);
    }

    public static <T> Map<String,String> convertObj2MapWithSon(T t,boolean dealSonFiledType){
        Field[] fields = t.getClass().getDeclaredFields();

        Map<String,String> paramMap = new HashMap<>();
        for (Field field:fields) {
            ConvertAnnotation annotation = field.getAnnotation(ConvertAnnotation.class);

            if (annotation != null && annotation.isSonFiled() == dealSonFiledType) {

                field.setAccessible(true);

                if (String.class.isAssignableFrom(field.getType())) {
                    try {
                        Object o = field.get(t);
                        if (o == null || StringUtils.isEmpty((String)o)) {
                            continue;
                        }
                        paramMap.put(annotation.mapKey(),(String)o);
                    } catch (Exception e) {
                        logger.error("数据转换异常",e);
                    }
                } else if (Long.class.isAssignableFrom(field.getType())) {
                    try {
                        Object o = field.get(t);
                        if (o == null) {
                            continue;
                        }

                        if (annotation.isFen2Yuan()) {
                            paramMap.put(annotation.mapKey(),AmountUtils.changeF2Y((Long)o));
                        } else {
                            paramMap.put(annotation.mapKey(),((Long)o).toString());
                        }
                    } catch (Exception e) {
                        logger.error("数据转换异常",e);
                    }
                }
            }
        }
        return paramMap;
    }




    public static void main(String[] args) {

        CovertDemo covertDemo = new CovertDemo();
        covertDemo.setName("试试");
        covertDemo.setAddress("地址hhhh");
        covertDemo.setMoney(100011L);
        covertDemo.setAihao("rap和篮球");
        covertDemo.setShengao(178L);
        covertDemo.setTizhong(66L);

        Map<String, String> stringStringMap = convertObj2Map(covertDemo);

        Map<String, String> sonMap = convertObj2MapWithSon(covertDemo,true);

        System.out.println(JSONObject.toJSONString(stringStringMap));
        System.out.println(JSONObject.toJSONString(sonMap));


        for (int i=0;i<10;i++) {
            String s = UUID.randomUUID().toString().replace("-", "");

            System.out.println(s +"      :"+s.length());
        }

    }



}

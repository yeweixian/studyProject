package com.danger.study.tools.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * 实体类对象属性拷贝工具
 * Created by danger on 2016/6/8.
 */
public class CopyUtils {

    public static void copyBean(Object source, Object dest) throws Exception {
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (PropertyDescriptor sourcePropertyDemo : sourceProperty) {
                for (PropertyDescriptor destPropertyDemo : destProperty) {
                    if (sourcePropertyDemo.getName().equals(destPropertyDemo.getName())) {
                        Object sourcePropertyObj = sourcePropertyDemo.getReadMethod().invoke(source);
                        destPropertyDemo.getWriteMethod().invoke(dest, sourcePropertyObj);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

}

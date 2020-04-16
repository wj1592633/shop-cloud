package com.shop.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ToolUtils {
    public static Map<String, Object> bean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return map;

    }

    /**
     * 去除map的value为null或者""的entry
     * @param map
     */
    public static void removeEmptyMapEntry(Map map){
        if(map != null && map.size() > 0){
            Set<Map.Entry> set = map.entrySet();
            Iterator<Map.Entry> iterator = set.iterator();
            while (iterator.hasNext()){
                Map.Entry next = iterator.next();
                Object value = next.getValue();
                if (null == value){
                    iterator.remove();
                }
                if(value instanceof String){
                    if(StringUtils.isBlank(((String)value))){
                        iterator.remove();
                    }
                }
            }

        }
    }

    public static <T> T map2Bean(Map<String, Object> beanMap, Class<T> beanClass) throws Exception {
        if (beanMap == null) {
            return null;
        }
        T t = beanClass.newInstance();
        BeanUtils.populate(t, beanMap);
        return t;
    }
}

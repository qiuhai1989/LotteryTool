package com.yuncai.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.EnumSet;

import org.springframework.beans.BeanUtils;

public class Enums {

	/**
	 * 从指定的枚举类中根据property搜寻匹配指定值的枚举实例
	 * 
	 * @param <T>
	 * @param enumClass
	 * @param property
	 * @param propValue
	 * @return
	 */
	public static <T extends Enum<T>> T fromEnumProperty(Class<T> clazz, String propertyName, Object propValue) {
		try {
			PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, propertyName);
			Method readMethod = property.getReadMethod();
			EnumSet<T> values = EnumSet.allOf(clazz);
			for (T t : values) {
				if(propValue.equals(readMethod.invoke(t))){
					return t;
				}
			}
			return null;
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

}

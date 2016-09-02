package com.yuncai.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * 集合工具类
 * @author ysh
 *
 */
public class Collections3 {
	/**
	 * 提取枚举中的对象的指定属性为key，对象为value, 组合成Map.
	 * 
	 * @param clazz 枚举类型.
	 * @param propertyName 要提取为Map中的Key值的属性名.
	 */
	public static <T extends Enum<T>> Map<Object, T> enumToMap(Class<T> clazz, String propertyName) {
		Map<Object, T> instanceMap = new HashMap<Object, T>();
		try {
			PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, propertyName);
			Method readMethod = property.getReadMethod();
			EnumSet<T> values = EnumSet.allOf(clazz);
			for (T value : values) {
				instanceMap.put(readMethod.invoke(value), value);
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
		return instanceMap;
	}
	/**
	 * 提取集合中的对象的指定属性为key，对象为value, 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取为Map中的Key值的属性名.
	 */
	public static <T> Map<Object, T> collectionToMap(Collection<T> collection, String propertyName) {
		Map<Object, T> instanceMap = new HashMap<Object, T>();
		try {
			for (T value : collection) {
				instanceMap.put(PropertyUtils.getProperty(value, propertyName), value);
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
		return instanceMap;
	}
	/**
	 * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
	 * 
	 * @param collection 来源集合.
	 * @param keyPropertyName 要提取为Map中的Key值的属性名.
	 * @param valuePropertyName 要提取为Map中的Value值的属性名.
	 */
	public static Map<Object,Object> extractToMap(final Collection<?> collection, final String keyPropertyName,final String valuePropertyName) {
		Map<Object,Object> map = new HashMap<Object,Object>(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		return map;
	}
	/**
	 * 提取对象的所有属性(通过Getter函数), 组合成Map.
	 * 
	 * @param bean 对象实例
	 */
	public static Map<Object,Object> beanToMap(final Object bean) {
		Map<Object,Object> map = new HashMap<Object,Object>();
		try {
			PropertyDescriptor[] propertyDescriptors=PropertyUtils.getPropertyDescriptors(bean.getClass());
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				try{
				Method methodGetX = propertyDescriptor.getReadMethod();//Read对应get()方法
				Object reValue = methodGetX.invoke(bean);
				map.put(propertyDescriptor.getName(),reValue);
				}catch (Exception e) {
					throw Exceptions.unchecked(e);
				}
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		return map;
	}
	/**
	 * 提取集合中的对象的指定属性(通过Getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static List<Object> extractToList(final Collection<?> collection, final String propertyName) {
		List<Object> list = new ArrayList<Object>(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		return list;
	}
	/**
	 * 提取集合中的对象的指定属性(通过Getter函数), 组合成Set.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	public static Set<Object> extractToSet(final Collection<?> collection, final String propertyName) {
		Set<Object> set = new HashSet<Object>(collection.size());
		try {
			for (Object obj : collection) {
				set.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		return set;
	}
	/**
	 * 提取集合中的对象的指定属性(通过Getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	public static String extractToString(final Collection<?> collection, final String propertyName, final String separator) {
		List<?> list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
	 */
	public static String convertToString(final Collection<?> collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
	 */
	public static String convertToString(final Collection<?> collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Map<?,?> map) {
		return (map == null || map.isEmpty());
	}
	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}

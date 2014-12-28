package org.yousuowei.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class Utils {

	public static Object toObject(Class<?> toClass, Object obj) {
		if (null == obj) {
			return null;
		} else {
			Object t = null;
			try {
				t = toClass.newInstance();
				BeanUtils.copyProperties(t,obj);
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			return t;
		}
	}

	public static List<Object> toObjectList(Class<?> toClass, List<Object> list) {
		List<Object> result = null;
		if (null != list && list.size() > 0) {
			result = new ArrayList<Object>(list.size());
			for (Object obj : list) {
				result.add(toObject(toClass, obj));
			}
		} else {
			result = new ArrayList<Object>(0);
		}
		return result;
	}

}

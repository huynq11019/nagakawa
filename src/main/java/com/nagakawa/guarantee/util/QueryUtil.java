/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagakawa.guarantee.util;

import java.lang.reflect.Field;

/**
 *
 * @author linhlh2
 */
public class QueryUtil {

	public static final Long ALL = -1L;
	public static final int FIRST_INDEX = 0;
	public static final int GET_ALL = -1;
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// LinhLH2 fix
	private static String _replaceSpecialCharacter(String param) {
		return param.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_").replaceAll("!", "\\\\!");
	}

	public static String addOrder(Class<?> c, String orderByColumn, String orderByType, String alias) {
		StringBuilder sb = new StringBuilder(6);

		if (hasProperty(c, orderByColumn) || hasProperty(c.getSuperclass(), orderByColumn)) {
			sb.append(" order by ");
			
			if(Validator.isNotNull(alias)) {
				sb.append(alias);
				sb.append(StringPool.PERIOD);
			}
			
			sb.append(orderByColumn);
			sb.append(StringPool.SPACE);
			
			sb.append(orderByType);
		}

		return sb.toString();
	}

	public static String getFullStringParam(String param) {
		StringBuilder sb = new StringBuilder(5);

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getFullStringParam(String param, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(5);

		param = StringUtil.trim(param);

		if (caseInsensitive) {
			param = param.toLowerCase();
		}

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getLeftStringParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	public static String getLeftStringParam(String param, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		if (caseInsensitive) {
			param = param.toLowerCase();
		}

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	public static String getRightStringParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getRightStringParam(String param, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		if (caseInsensitive) {
			param = param.toLowerCase();
		}

		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getStringParam(String param) {
		StringBuilder sb = new StringBuilder(1);

		param = StringUtil.trim(param);

		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	public static String getStringParam(String param, boolean caseInsensitive) {
		StringBuilder sb = new StringBuilder(1);

		param = StringUtil.trim(param);

		if (caseInsensitive) {
			param = param.toLowerCase();
		}

		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	private static boolean hasProperty(Class<?> c, String name) {
		try {
			Field[] fields = c.getDeclaredFields();

			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase(name)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.nagakawa.guarantee.util;

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

	public static String addOrder(Class<?> c, String orderByColumn, String orderByType, String alias) {
		StringBuilder sb = new StringBuilder(6);

		if (ReflectionUtil.hasProperty(c, orderByColumn)
				|| ReflectionUtil.hasProperty(c.getSuperclass(), orderByColumn)) {
			sb.append(" order by ");

			if (Validator.isNotNull(alias)) {
				sb.append(alias);
				sb.append(StringPool.PERIOD);
			}

			sb.append(orderByColumn);
			sb.append(StringPool.SPACE);

			sb.append(orderByType);
		}

		return sb.toString();
	}

	public static String createOrderQuery(Class<?> entityClass, String orderByType, String orderByColumn) {
		if (Validator.isNull(orderByType) || Validator.isNull(orderByColumn)) {
			return "order by e.lastModifiedDate desc";
		}

		StringBuilder sql = new StringBuilder();

		orderByType = QueryUtil.ASC.equalsIgnoreCase(orderByType) ? QueryUtil.ASC : QueryUtil.DESC;

		sql.append(addOrder(entityClass, orderByColumn, orderByType, "e"));

		if (sql.length() > 0) {
			sql.append(", e.lastModifiedDate desc");
		} else {
			sql.append(" order by e.lastModifiedDate desc");
		}

		return sql.toString();

	}

	public static String getFullStringParam(String param) {
		StringBuilder sb = new StringBuilder(5);

		param = StringUtil.trim(param);

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getFullWildcardParam(String param) {
		StringBuilder sb = new StringBuilder(5);

		param = StringUtil.trim(param);

		sb.append(StringPool.STAR);
		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.STAR);

		return sb.toString();
	}

	public static String getLeftStringParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		sb.append(StringPool.PERCENT);
		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	public static String getLeftWildcardParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		sb.append(StringPool.STAR);
		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	public static String getRightStringParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.PERCENT);

		return sb.toString();
	}

	public static String getRightWildcardParam(String param) {
		StringBuilder sb = new StringBuilder(2);

		param = StringUtil.trim(param);

		sb.append(_replaceSpecialCharacter(param));
		sb.append(StringPool.STAR);

		return sb.toString();
	}

	public static String getStringParam(String param) {
		StringBuilder sb = new StringBuilder(1);

		param = StringUtil.trim(param);

		sb.append(_replaceSpecialCharacter(param));

		return sb.toString();
	}

	private static String _replaceSpecialCharacter(String param) {
		return param.replaceAll("%", "\\\\%").replaceAll("_", "\\\\_").replaceAll("!", "\\\\!");
	}
}

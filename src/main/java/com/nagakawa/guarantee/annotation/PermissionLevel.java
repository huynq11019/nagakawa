/**
 * 
 */
package com.nagakawa.guarantee.annotation;

/**
 * @author LinhLH
 *
 */
public enum PermissionLevel {
	/**
	 * Level owner là level chỉ cho phép owner thực hiện các action trên các bản ghi của mình
	 */
	OWNER,
	/**
	 * Level area là level chỉ cho phép người dùng ở cấp Tỉnh/Thành phố, Quận/Huyện, Phường/Xã thao tác với các
	 * bản ghi ở trong khu vực của mình. 
	 */
	AREA
}

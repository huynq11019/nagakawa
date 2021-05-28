/**
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nagakawa.guarantee.util;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author os_linhlh2
 */
public class ArrayUtil {

	/**
	 * Hàm ghép nhiều mảng kiểu boolean thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * boolean[] a1 = new boolean[]{true, false}; <br/>
	 * boolean[] a2 = new boolean[]{false, false}; <br/>
	 * <br/>
	 * boolean[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * true <br/>
	 * false <br/>
	 * false <br/>
	 * false
	 * 
	 * @param arrays Danh sách mảng kiểu boolean, không giới hạn số lượng mảng
	 *               truyền vào
	 * @return Mảng boolean sau khi ghép tất cả các mảng lại
	 */
	public static boolean[] append(boolean[]... arrays) {
		int length = 0;

		for (boolean[] array : arrays) {
			length += array.length;
		}

		boolean[] newArray = new boolean[length];

		int previousLength = 0;

		for (boolean[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu boolean vào một mảng kiểu boolean cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * boolean[] a1 = new boolean[]{true, false}; <br/>
	 * boolean a2 = false; <br/>
	 * <br/>
	 * boolean[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * true <br/>
	 * false <br/>
	 * false
	 * 
	 * @param array Mảng kiểu boolean ban đầu
	 * @param value giá trị kiểu boolean cần thêm vào mảng
	 * @return Mảng boolean mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static boolean[] append(boolean[] array, boolean value) {
		boolean[] newArray = new boolean[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu byte thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * byte[] a1 = new byte[]{(byte)0xe0, 0x4f}; <br/>
	 * byte[] a2 = new byte[]{(byte)0xba, (byte)0x8a}; <br/>
	 * <br/>
	 * byte[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * -32 <br/>
	 * 79 <br/>
	 * -70 <br/>
	 * -118
	 * 
	 * @param arrays Danh sách mảng kiểu byte, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng byte sau khi ghép tất cả các mảng lại
	 */
	public static byte[] append(byte[]... arrays) {
		int length = 0;

		for (byte[] array : arrays) {
			length += array.length;
		}

		byte[] newArray = new byte[length];

		int previousLength = 0;

		for (byte[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu byte vào một mảng kiểu byte cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * byte[] a1 = new byte[]{(byte)0xe0, 0x4f}; <br/>
	 * byte a2 = (byte)0xba; <br/>
	 * <br/>
	 * byte[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * -32 <br/>
	 * 79 <br/>
	 * -70
	 * 
	 * @param array Mảng kiểu byte ban đầu
	 * @param value giá trị kiểu byte cần thêm vào mảng
	 * @return Mảng byte mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static byte[] append(byte[] array, byte value) {
		byte[] newArray = new byte[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu char thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * char[] a1 = new char[]{'a', 'b'}; <br/>
	 * char[] a2 = new char[]{'c', 'd'}; <br/>
	 * <br/>
	 * char[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * a <br/>
	 * b <br/>
	 * c <br/>
	 * d
	 * 
	 * @param arrays Danh sách mảng kiểu char, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng char sau khi ghép tất cả các mảng lại
	 */
	public static char[] append(char[]... arrays) {
		int length = 0;

		for (char[] array : arrays) {
			length += array.length;
		}

		char[] newArray = new char[length];

		int previousLength = 0;

		for (char[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu char vào một mảng kiểu char cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * char[] a1 = new char[]{'a', 'b'}; <br/>
	 * char a2 = 'c'; <br/>
	 * <br/>
	 * char[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * a <br/>
	 * b <br/>
	 * c
	 * 
	 * @param array Mảng kiểu char ban đầu
	 * @param value giá trị kiểu char cần thêm vào mảng
	 * @return Mảng char mới sau khi thêm phần tử vào mảng ban đầu
	 */

	public static char[] append(char[] array, char value) {
		char[] newArray = new char[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu double thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * double[] a1 = new double[]{1.1, 2.2}; <br/>
	 * double[] a2 = new double[]{3.3, 4.4}; <br/>
	 * <br/>
	 * double[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1.1 <br/>
	 * 2.2 <br/>
	 * 3.3 <br/>
	 * 4.4
	 * 
	 * @param arrays Danh sách mảng kiểu double, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng double sau khi ghép tất cả các mảng lại
	 */
	public static double[] append(double[]... arrays) {
		int length = 0;

		for (double[] array : arrays) {
			length += array.length;
		}

		double[] newArray = new double[length];

		int previousLength = 0;

		for (double[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu double vào một mảng kiểu double cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * double[] a1 = new double[]{1.1, 2.2}; <br/>
	 * double a2 = 3.3; <br/>
	 * <br/>
	 * double[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1.1 <br/>
	 * 2.2 <br/>
	 * 3.3
	 * 
	 * @param array Mảng kiểu double ban đầu
	 * @param value giá trị kiểu double cần thêm vào mảng
	 * @return Mảng double mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static double[] append(double[] array, double value) {
		double[] newArray = new double[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu float thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * float[] a1 = new float[]{1.1f, 2.2f}; <br/>
	 * float[] a2 = new float[]{3.3f, 4.4f}; <br/>
	 * <br/>
	 * float[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1.1 <br/>
	 * 2.2 <br/>
	 * 3.3 <br/>
	 * 4.4
	 * 
	 * @param arrays Danh sách mảng kiểu float, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng float sau khi ghép tất cả các mảng lại
	 */
	public static float[] append(float[]... arrays) {
		int length = 0;

		for (float[] array : arrays) {
			length += array.length;
		}

		float[] newArray = new float[length];

		int previousLength = 0;

		for (float[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu float vào một mảng kiểu float cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * float[] a1 = new float[]{1.1f, 2.2f}; <br/>
	 * float a2 = 3.3f; <br/>
	 * <br/>
	 * float[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1.1 <br/>
	 * 2.2 <br/>
	 * 3.3
	 * 
	 * @param array Mảng kiểu float ban đầu
	 * @param value giá trị kiểu float cần thêm vào mảng
	 * @return Mảng float mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static float[] append(float[] array, float value) {
		float[] newArray = new float[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu int thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * int[] a1 = new int[]{1, 2}; <br/>
	 * int[] a2 = new int[]{3, 4}; <br/>
	 * <br/>
	 * int[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param arrays Danh sách mảng kiểu int, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng int sau khi ghép tất cả các mảng lại
	 */
	public static int[] append(int[]... arrays) {
		int length = 0;

		for (int[] array : arrays) {
			length += array.length;
		}

		int[] newArray = new int[length];

		int previousLength = 0;

		for (int[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu int vào một mảng kiểu int cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * int[] a1 = new int[]{1, 2}; <br/>
	 * int a2 = 3; <br/>
	 * <br/>
	 * int[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3
	 * 
	 * @param array Mảng kiểu int ban đầu
	 * @param value giá trị kiểu int cần thêm vào mảng
	 * @return Mảng int mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static int[] append(int[] array, int value) {
		int[] newArray = new int[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu long thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * long[] a1 = new long[]{1L, 2L}; <br/>
	 * long[] a2 = new long[]{3L, 4L}; <br/>
	 * <br/>
	 * long[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param arrays Danh sách mảng kiểu long, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng long sau khi ghép tất cả các mảng lại
	 */
	public static long[] append(long[]... arrays) {
		int length = 0;

		for (long[] array : arrays) {
			length += array.length;
		}

		long[] newArray = new long[length];

		int previousLength = 0;

		for (long[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu long vào một mảng kiểu long cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * long[] a1 = new long[]{1L, 2L}; <br/>
	 * long a2 = 3L; <br/>
	 * <br/>
	 * long[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3
	 * 
	 * @param array Mảng kiểu long ban đầu
	 * @param value giá trị kiểu long cần thêm vào mảng
	 * @return Mảng long mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static long[] append(long[] array, long value) {
		long[] newArray = new long[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng kiểu short thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * short[] a1 = new short[]{1, 2}; <br/>
	 * short[] a2 = new short[]{3, 4}; <br/>
	 * <br/>
	 * short[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(short i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param arrays Danh sách mảng kiểu short, không giới hạn số lượng mảng truyền
	 *               vào
	 * @return Mảng short sau khi ghép tất cả các mảng lại
	 */
	public static short[] append(short[]... arrays) {
		int length = 0;

		for (short[] array : arrays) {
			length += array.length;
		}

		short[] newArray = new short[length];

		int previousLength = 0;

		for (short[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm giá trị kiểu short vào một mảng kiểu short cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * short[] a1 = new short[]{1, 2}; <br/>
	 * short a2 = 3; <br/>
	 * <br/>
	 * short[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(short i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3
	 * 
	 * @param array Mảng kiểu short ban đầu
	 * @param value giá trị kiểu short cần thêm vào mảng
	 * @return Mảng short mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static short[] append(short[] array, short value) {
		short[] newArray = new short[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = value;

		return newArray;
	}

	/**
	 * Hàm ghép nhiều mảng đối tượng thành một mảng duy nhất
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L}; <br/>
	 * Long[] a2 = new Long[]{3L, 4L}; <br/>
	 * <br/>
	 * Long[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param arrays Danh sách mảng đối tượng, không giới hạn số lượng mảng truyền
	 *               vào. Đối tượng có thể là Object, String, Long, Integer, Short,
	 *               Double, Float hoặc 1 đối tượng bất kỳ.
	 * @return Mảng đối tượng sau khi ghép tất cả các mảng lại
	 */
	public static <T> T[] append(T[]... arrays) {
		int length = 0;

		for (T[] array : arrays) {
			length += array.length;
		}

		Class<?> arraysClass = arrays[0].getClass();

		T[] newArray = (T[]) Array.newInstance(arraysClass.getComponentType(), length);

		int previousLength = 0;

		for (T[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	/**
	 * Hàm thêm đối tượng A vào một mảng đối tượng A
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L}; <br/>
	 * Long a2 = 3L; <br/>
	 * <br/>
	 * Long[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3
	 * 
	 * @param array Mảng đối tượng ban đầu
	 * @param value Đối tượng cần thêm vào mảng.Đối tượng có thể là Object, String,
	 *              Long, Integer, Short, Double, Float hoặc 1 đối tượng bất kỳ.
	 * @return Mảng mới sau khi thêm phần tử vào mảng ban đầu
	 */
	public static <T> T[] append(T[] array, T value) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[]) Array.newInstance(arrayClass.getComponentType(), array.length + 1);

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[array.length] = value;

		return newArray;
	}

	/**
	 * Hàm ghép 2 mảng đối tượng thành một mảng duy nhất Đối tượng có thể là Object,
	 * String, Long, Integer, Short, Double, Float hoặc 1 đối tượng bất kỳ
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L}; <br/>
	 * Long[] a2 = new Long[]{3L, 4L}; <br/>
	 * <br/>
	 * Long[] all = ArrayUtil.append(a1, a2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param array1 Mảng thứ nhất
	 * @param array2 Mảng thứ hai
	 * @return Mảng sau khi ghép 2 mảng lại
	 */
	public static <T> T[] append(T[] array1, T[] array2) {
		Class<?> array1Class = array1.getClass();

		T[] newArray = (T[]) Array.newInstance(array1Class.getComponentType(), array1.length + array2.length);

		System.arraycopy(array1, 0, newArray, 0, array1.length);

		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	/**
	 * Hàm thêm một mảng đối tượng A vào một mảng đối tượng A 2 chiều Đối tượng có
	 * thể là Object, String, Long, Integer, Short, Double, Float hoặc 1 đối tượng
	 * bất kỳ
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L}; <br/>
	 * Long[] a2 = new Long[]{3L, 4L}; <br/>
	 * Long[] a3 = new Long[]{5L, 6L}; <br/>
	 * <br/>
	 * Long[][] aa = new Long[][]{a1,a2}; <br/>
	 * <br/>
	 * Long[][] all = ArrayUtil.append(aa, a3); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;for(int j=0; j< all[i].length; j++) { <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * &nbsp;&nbsp;} <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4 <br/>
	 * 5 <br/>
	 * 6
	 * 
	 * @param array1 Mảng hai chiều ban đầu
	 * @param value  Mảng một chiều cần thêm vào
	 * @return Mảng Long hai chiều sau khi ghép
	 */
	public static <T> T[][] append(T[][] array1, T[] value) {
		Class<?> array1Class = array1.getClass();

		T[][] newArray = (T[][]) Array.newInstance(array1Class.getComponentType(), array1.length + 1);

		System.arraycopy(array1, 0, newArray, 0, array1.length);

		newArray[array1.length] = value;

		return newArray;
	}

	/**
	 * Hàm ghép 2 mảng đối tượng 2 chiều thành 1 Đối tượng có thể là Object, String,
	 * Long, Integer, Short, Double, Float hoặc 1 đối tượng bất kỳ
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L}; <br/>
	 * Long[] a2 = new Long[]{3L, 4L}; <br/>
	 * Long[] a3 = new Long[]{5L, 6L}; <br/>
	 * Long[] a4 = new Long[]{7L, 8L}; <br/>
	 * <br/>
	 * Long[][] aa1 = new Long[][]{a1,a2}; <br/>
	 * Long[][] aa2 = new Long[][]{a3,a4}; <br/>
	 * <br/>
	 * Long[][] all = ArrayUtil.append(aa1, aa2); <br/>
	 * <br/>
	 * for(int i=0; i &lt; all.length; i++) { <br/>
	 * &nbsp;&nbsp;for(int j=0; j< all[i].length; j++) { <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;System.out.print(all[i]+"\n"); <br/>
	 * &nbsp;&nbsp;} <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4 <br/>
	 * 5 <br/>
	 * 6 <br/>
	 * 7 <br/>
	 * 8
	 * 
	 * @param arrays Danh sách mảng, không giới hạn số lượng mảng truyền vào
	 * @return Mảng đối tượng mới sau khi ghép tất cả các mảng lại
	 */
	public static <T> T[][] append(T[][] array1, T[][] array2) {
		Class<?> array1Class = array1.getClass();

		T[][] newArray = (T[][]) Array.newInstance(array1Class.getComponentType(), array1.length + array2.length);

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu boolean thành một mảng kiểu boolean khác giống hệt
	 * mảng ban đầu
	 * 
	 * @param array Mảng kiểu boolean
	 */
	public static boolean[] clone(boolean[] array) {
		boolean[] newArray = new boolean[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu boolean từ vị trí a0 đến một vị trí a0+n sang 1 mảng
	 * kiểu boolean mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * boolean[] a1 = new boolean[]{true, false, true, true, false, true}; <br/>
	 * <br/>
	 * boolean[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * true <br/>
	 * true
	 * 
	 * @param array Mảng kiểu boolean
	 * @return Mảng kiểu boolean mới
	 */
	public static boolean[] clone(boolean[] array, int from, int to) {
		boolean[] newArray = new boolean[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu byte thành một mảng kiểu byte khác giống hệt mảng ban
	 * đầu
	 * 
	 * @param array Mảng kiểu byte
	 */
	public static byte[] clone(byte[] array) {
		byte[] newArray = new byte[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu byte từ vị trí a0 đến một vị trí a0+n sang 1 mảng kiểu
	 * byte mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * byte[] a1 = new byte[]{(byte)0xe0, 0x4f, (byte)0xba, (byte)0x8a, (byte)0x98,
	 * (byte)0xa8}; <br/>
	 * <br/>
	 * byte[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * -70 <br/>
	 * -118
	 * 
	 * @param array Mảng kiểu byte
	 * @return Mảng kiểu byte mới
	 */
	public static byte[] clone(byte[] array, int from, int to) {
		byte[] newArray = new byte[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu char thành một mảng kiểu char khác giống hệt mảng ban
	 * đầu
	 * 
	 * @param array Mảng kiểu char
	 */
	public static char[] clone(char[] array) {
		char[] newArray = new char[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu char từ vị trí a0 đến một vị trí a0+n sang 1 mảng kiểu
	 * char mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * char[] a1 = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'}; <br/>
	 * <br/>
	 * char[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * c <br/>
	 * d
	 * 
	 * @param array Mảng kiểu char
	 * @return Mảng kiểu char mới
	 */
	public static char[] clone(char[] array, int from, int to) {
		char[] newArray = new char[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu double thành một mảng kiểu double khác giống hệt mảng
	 * ban đầu
	 * 
	 * @param array Mảng kiểu double
	 */
	public static double[] clone(double[] array) {
		double[] newArray = new double[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu double từ vị trí a0 đến một vị trí a0+n sang 1 mảng
	 * kiểu double mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * double[] a1 = new double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6}; <br/>
	 * <br/>
	 * double[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3.3 <br/>
	 * 4.4
	 * 
	 * @param array Mảng kiểu double
	 * @return Mảng kiểu double mới
	 */
	public static double[] clone(double[] array, int from, int to) {
		double[] newArray = new double[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu float thành một mảng kiểu float khác giống hệt mảng
	 * ban đầu
	 * 
	 * @param array Mảng kiểu float
	 */
	public static float[] clone(float[] array) {
		float[] newArray = new float[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu float từ vị trí a0 đến một vị trí a0+n sang 1 mảng
	 * kiểu float mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * float[] a1 = new float[]{1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f}; <br/>
	 * <br/>
	 * float[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3.3 <br/>
	 * 4.4
	 * 
	 * @param array Mảng kiểu float
	 * @return Mảng kiểu float mới
	 */
	public static float[] clone(float[] array, int from, int to) {
		float[] newArray = new float[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu int thành một mảng kiểu int khác giống hệt mảng ban
	 * đầu
	 * 
	 * @param array Mảng kiểu int
	 */
	public static int[] clone(int[] array) {
		int[] newArray = new int[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu int từ vị trí a0 đến một vị trí a0+n sang 1 mảng kiểu
	 * int mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * int[] a1 = new int[]{1, 2, 3, 4, 5, 6}; <br/>
	 * <br/>
	 * int[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param array Mảng kiểu int
	 * @return Mảng kiểu int mới
	 */
	public static int[] clone(int[] array, int from, int to) {
		int[] newArray = new int[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu long thành một mảng kiểu long khác giống hệt mảng ban
	 * đầu
	 * 
	 * @param array Mảng kiểu long
	 */
	public static long[] clone(long[] array) {
		long[] newArray = new long[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu long từ vị trí a0 đến một vị trí a0+n sang 1 mảng kiểu
	 * long mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * long[] a1 = new long[]{1L, 2L, 3L, 4L, 5L, 6L}; <br/>
	 * <br/>
	 * long[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param array Mảng kiểu long
	 * @return Mảng kiểu long mới
	 */
	public static long[] clone(long[] array, int from, int to) {
		long[] newArray = new long[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu short thành một mảng kiểu short khác giống hệt mảng
	 * ban đầu
	 * 
	 * @param array Mảng kiểu short
	 */
	public static short[] clone(short[] array) {
		short[] newArray = new short[array.length];

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng kiểu short từ vị trí a0 đến một vị trí a0+n sang 1 mảng
	 * kiểu short mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * short[] a1 = new short[]{1, 2, 3, 4, 5, 6}; <br/>
	 * <br/>
	 * short[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(short i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param array Mảng kiểu short
	 * @return Mảng kiểu short mới
	 */
	public static short[] clone(short[] array, int from, int to) {
		short[] newArray = new short[to - from];

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng đối tượng thành một mảng khác giống hệt mảng ban đầu
	 * 
	 * @param array Mảng đối tượng ,có thể là Object, String, Long, Integer, Short,
	 *              Double, Float hoặc 1 đối tượng bất kỳ
	 */
	public static <T> T[] clone(T[] array) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[]) Array.newInstance(arrayClass.getComponentType(), array.length);

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng đối tượng từ vị trí a0 đến một vị trí a0+n sang 1 mảng mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L, 3L, 4L, 5L, 6L}; <br/>
	 * <br/>
	 * Long[] clone = ArrayUtil.clone(a1, 2, 4); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(clone[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 3 <br/>
	 * 4
	 * 
	 * @param array Mảng đối tượng
	 * @return Mảng mới
	 */
	public static <T> T[] clone(T[] array, int from, int to) {
		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[]) Array.newInstance(arrayClass.getComponentType(), to - from);

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm copy một mảng đối tượng 2 chiều thành một mảng khác giống hệt mảng ban
	 * đầu
	 * 
	 * @param array Mảng đối tượng ,có thể là Object, String, Long, Integer, Short,
	 *              Double, Float hoặc 1 đối tượng bất kỳ
	 */
	public static <T> T[][] clone(T[][] array) {
		Class<?> arrayClass = array.getClass();

		T[][] newArray = (T[][]) Array.newInstance(arrayClass.getComponentType(), array.length);

		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	/**
	 * Hàm copy một mảng đối tượng 2 chiều từ vị trí a0 đến một vị trí a0+n sang 1
	 * mảng mới
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L, 3L}; <br/>
	 * Long[] a2 = new Long[]{4L, 5L, 6L}; <br/>
	 * Long[] a3 = new Long[]{7L, 8L, 9L}; <br/>
	 * Long[] a4 = new Long[]{10L, 11L, 12L}; <br/>
	 * <br/>
	 * Long[][] aa = new Long[][]{a1, a2, a3, a4}; <br/>
	 * <br/>
	 * Long[] clone = ArrayUtil.clone(aa, 1, 3); <br/>
	 * <br/>
	 * for(int i=0; i &lt; clone.length; i++) { <br/>
	 * &nbsp;&nbsp;for(int j=0; j< clone[i].length; j++) { <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;System.out.print(clone[i][j]+"\n"); <br/>
	 * &nbsp;&nbsp;} <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 4 <br/>
	 * 5 <br/>
	 * 6 <br/>
	 * 7 <br/>
	 * 8 <br/>
	 * 9
	 * 
	 * @param array Mảng đối tượng
	 * @return Mảng mới
	 */
	public static <T> T[][] clone(T[][] array, int from, int to) {
		Class<?> arrayClass = array.getClass();

		T[][] newArray = (T[][]) Array.newInstance(arrayClass.getComponentType(), to - from);

		System.arraycopy(array, from, newArray, 0, Math.min(array.length - from, newArray.length));

		return newArray;
	}

	/**
	 * Hàm ghép 2 mảng object vào 1 mảng cho trước
	 * <p>
	 * <b>Ví dụ :</b> <br/>
	 * Long[] a1 = new Long[]{1L, 2L, 3L}; <br/>
	 * Long[] a2 = new Long[]{4L, 5L, 6L}; <br/>
	 * <br/>
	 * Long[] combine = new Long[a1.length+a2.length]; <br/>
	 * <br/>
	 * ArrayUtil.combine(a1, a2, combine); <br/>
	 * <br/>
	 * for(int i=0; i &lt; combine.length; i++) { <br/>
	 * &nbsp;&nbsp;System.out.print(combine[i]+"\n"); <br/>
	 * } <br/>
	 * <b>Kết quả :</b> <br/>
	 * 1 <br/>
	 * 2 <br/>
	 * 3 <br/>
	 * 4 <br/>
	 * 5 <br/>
	 * 6
	 * 
	 * @param array1        Mảng thứ nhất
	 * @param array2        Mảng thứ hai
	 * @param combinedArray Mảng chứa tất cả các phần tử của 2 mảng trên. Độ dài của
	 *                      mảng này phải lớn hơn hoặc bẳng tổng độ dài 2 mảng trên
	 *                      cộng lại
	 */
	public static void combine(Object[] array1, Object[] array2, Object[] combinedArray) {

		System.arraycopy(array1, 0, combinedArray, 0, array1.length);

		System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu boolean
	 * @param value Phần tử kiểu boolean cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(boolean[] array, boolean value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu byte
	 * @param value Phần tử kiểu byte cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(byte[] array, byte value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu char
	 * @param value Phần tử kiểu char cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(char[] array, char value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu double
	 * @param value Phần tử kiểu double cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(double[] array, double value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu float
	 * @param value Phần tử kiểu float cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(float[] array, float value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu int
	 * @param value Phần tử kiểu int cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(int[] array, int value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu long
	 * @param value Phần tử kiểu long cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(long[] array, long value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu Object
	 * @param value Phần tử kiểu Object cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(Object[] array, Object value) {
		if ((array == null) || (array.length == 0) || (value == null)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value.equals(array[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm kiểm tra mảng array có chứa phần tử value hay không
	 * 
	 * @param array Mảng kiểu short
	 * @param value Phần tử kiểu short cần kiểm tra
	 * @return <b>true</b> nếu mảng có chứa phần tử cần kiểm tra
	 */
	public static boolean contains(short[] array, short value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (int i = 0; i < array.length; i++) {
			if (value == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Hàm loại bỏ các phần tử trùng nhau trong mảng
	 * 
	 * @param array Mảng cần loại bỏ phần tử trùng
	 * @return Mảng sau khi đã loại bỏ các phần tử trùng
	 */
	public static String[] distinct(String[] array) {
		return distinct(array, null);
	}

	/**
	 * Hàm loại bỏ các phần tử trùng nhau trong mảng và sắp xếp mảng theo điều kiện
	 * cho trước
	 * 
	 * @param array      Mảng cần loại bỏ phần tử trùng
	 * @param comparator Điều kiện sắp xếp cho trước. Nếu comparator = null thì mảng
	 *                   sẽ không sắp xếp.
	 * @return Mảng sau khi đã loại bỏ các phần tử trùng và sắp xếp theo thứ tự.
	 */
	public static String[] distinct(String[] array, Comparator<String> comparator) {

		if ((array == null) || (array.length == 0)) {
			return array;
		}

		Set<String> set = null;

		if (comparator == null) {
			set = new TreeSet<String>();
		} else {
			set = new TreeSet<String>(comparator);
		}

		for (int i = 0; i < array.length; i++) {
			String s = array[i];

			if (!set.contains(s)) {
				set.add(s);
			}
		}

		return set.toArray(new String[set.size()]);
	}

	/**
	 * Hàm lấy độ dài mảng
	 * 
	 * @param array Mảng cần lấy độ dài
	 * @return Độ dài của mảng
	 */
	public static int getLength(Object[] array) {
		if (array == null) {
			return 0;
		} else {
			return array.length;
		}
	}

	/**
	 * Hàm lấy ra phần tử ở vị trí <i>pos</i> của mảng
	 * 
	 * @param array Mảng cần duyệt
	 * @param pos   Vị trí phần tử cần lấy trong mảng
	 * @return Đối tượng cần lấy ra.
	 */
	public static Object getValue(Object[] array, int pos) {
		if ((array == null) || (array.length <= pos)) {
			return null;
		} else {
			return array[pos];
		}
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>boolean</i> cần duyệt
	 * @param value Phần tử kiểu <i>boolean</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static boolean[] remove(boolean[] array, boolean value) {
		List<Boolean> list = new ArrayList<Boolean>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Boolean(array[i]));
			}
		}

		return toArray(list.toArray(new Boolean[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>byte</i> cần duyệt
	 * @param value Phần tử kiểu <i>byte</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static byte[] remove(byte[] array, byte value) {
		List<Byte> list = new ArrayList<Byte>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Byte(array[i]));
			}
		}

		return toArray(list.toArray(new Byte[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>char</i> cần duyệt
	 * @param value Phần tử kiểu <i>char</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static char[] remove(char[] array, char value) {
		List<Character> list = new ArrayList<Character>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Character(array[i]));
			}
		}

		return toArray(list.toArray(new Character[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>double</i> cần duyệt
	 * @param value Phần tử kiểu <i>double</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static double[] remove(double[] array, double value) {
		List<Double> list = new ArrayList<Double>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Double(array[i]));
			}
		}

		return toArray(list.toArray(new Double[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>int</i> cần duyệt
	 * @param value Phần tử kiểu <i>int</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static int[] remove(int[] array, int value) {
		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Integer(array[i]));
			}
		}

		return toArray(list.toArray(new Integer[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>long</i> cần duyệt
	 * @param value Phần tử kiểu <i>long</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static long[] remove(long[] array, long value) {
		List<Long> list = new ArrayList<Long>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Long(array[i]));
			}
		}

		return toArray(list.toArray(new Long[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>short</i> cần duyệt
	 * @param value Phần tử kiểu <i>short</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static short[] remove(short[] array, short value) {
		List<Short> list = new ArrayList<Short>();

		for (int i = 0; i < array.length; i++) {
			if (value != array[i]) {
				list.add(new Short(array[i]));
			}
		}

		return toArray(list.toArray(new Short[list.size()]));
	}

	/**
	 * Hàm xóa tất cả phần tử trong mảng nếu mảng chứa phần tử đó
	 * 
	 * @param array Mảng kiểu <i>String</i> cần duyệt
	 * @param value Phần tử kiểu <i>String</i> cần xóa khỏi mảng
	 * @return Mảng sau khi xử lý.
	 */
	public static String[] remove(String[] array, String value) {
		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (!s.equals(value)) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * Hàm xóa tất cả phần tử kiểu <i>String</i> trong mảng kiểu <i>String</i> nếu
	 * mảng chứa các phần tử bắt đầu bằng chuỗi <i>prefix</i>
	 * 
	 * @param array  Mảng kiểu <i>String</i> cần duyệt
	 * @param prefix Chuỗi String dùng để kiểm tra các phần tử trong mảng. Nếu phần
	 *               tử nào bắt đầu bằng chuỗi này sẽ bị xóa khỏi mảng.
	 * @return Mảng sau khi xử lý.
	 */
	public static String[] removeByPrefix(String[] array, String prefix) {
		List<String> list = new ArrayList<String>();

		for (String s : array) {
			if (!s.startsWith(prefix)) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>byte</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(byte[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			byte value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>char</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(char[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			char value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>double</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(double[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			double value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>float</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(float[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			float value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>int</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(int[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			int value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>long</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(long[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			long value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>String</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(Object[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			Object value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm đảo ngược một mảng kiểu <i>float</i>
	 * 
	 * @param array Mảng cần xử lý
	 */
	public static void reverse(short[] array) {
		for (int left = 0, right = array.length - 1; left < right; left++, right--) {

			short value = array[left];

			array[left] = array[right];
			array[right] = value;
		}
	}

	/**
	 * Hàm cắt một mảng kiểu <i>boolean</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static boolean[] subset(boolean[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		boolean[] newArray = new boolean[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>byte</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static byte[] subset(byte[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		byte[] newArray = new byte[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>char</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static char[] subset(char[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		char[] newArray = new char[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>double</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static double[] subset(double[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		double[] newArray = new double[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>float</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static float[] subset(float[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		float[] newArray = new float[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>int</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static int[] subset(int[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		int[] newArray = new int[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>long</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static long[] subset(long[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		long[] newArray = new long[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng kiểu <i>short</i> chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static short[] subset(short[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		short[] newArray = new short[end - start];

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm cắt một mảng đối tượng bất kỳ chỉ giữ lại các phần tử từ vị trí
	 * <i>start</i> đến trước vị trí <i>end</i>
	 * 
	 * @param array Mảng cần xử lý
	 * @param start Vị trí phần tử bắt đầu
	 * @param end   Vị trí phần tử kết thúc
	 * @return Mảng sau khi cắt
	 */
	public static <T> T[] subset(T[] array, int start, int end) {
		if ((start < 0) || (end < 0) || ((end - start) < 0)) {
			return array;
		}

		Class<?> arrayClass = array.getClass();

		T[] newArray = (T[]) Array.newInstance(arrayClass.getComponentType(), end - start);

		System.arraycopy(array, start, newArray, 0, end - start);

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>boolean</i> sang kiểu <i>Boolean</i>
	 * 
	 * @param array Mảng kiểu boolean
	 * @return Mảng kiểu Boolean
	 */
	public static Boolean[] toArray(boolean[] array) {
		Boolean[] newArray = new Boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Boolean.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Boolean</i> sang kiểu <i>boolean</i>
	 * 
	 * @param array Mảng kiểu Boolean
	 * @return Mảng kiểu boolean
	 */
	public static boolean[] toArray(Boolean[] array) {
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].booleanValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>byte</i> sang kiểu <i>Byte</i>
	 * 
	 * @param array Mảng kiểu byte
	 * @return Mảng kiểu Byte
	 */
	public static Byte[] toArray(byte[] array) {
		Byte[] newArray = new Byte[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Byte.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Byte</i> sang kiểu <i>byte</i>
	 * 
	 * @param array Mảng kiểu Byte
	 * @return Mảng kiểu byte
	 */
	public static byte[] toArray(Byte[] array) {
		byte[] newArray = new byte[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].byteValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>char</i> sang kiểu <i>Character</i>
	 * 
	 * @param array Mảng kiểu char
	 * @return Mảng kiểu Character
	 */
	public static Character[] toArray(char[] array) {
		Character[] newArray = new Character[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Character.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Character</i> sang kiểu <i>char</i>
	 * 
	 * @param array Mảng kiểu Character
	 * @return Mảng kiểu char
	 */
	public static char[] toArray(Character[] array) {
		char[] newArray = new char[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].charValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>double</i> sang kiểu <i>Double</i>
	 * 
	 * @param array Mảng kiểu double
	 * @return Mảng kiểu Double
	 */
	public static Double[] toArray(double[] array) {
		Double[] newArray = new Double[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Double(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Double</i> sang kiểu <i>double</i>
	 * 
	 * @param array Mảng kiểu Double
	 * @return Mảng kiểu double
	 */
	public static double[] toArray(Double[] array) {
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].doubleValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>float</i> sang kiểu <i>Float</i>
	 * 
	 * @param array Mảng kiểu float
	 * @return Mảng kiểu Float
	 */
	public static Float[] toArray(float[] array) {
		Float[] newArray = new Float[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Float(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Float</i> sang kiểu <i>float</i>
	 * 
	 * @param array Mảng kiểu Float
	 * @return Mảng kiểu float
	 */
	public static float[] toArray(Float[] array) {
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].floatValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>int</i> sang kiểu <i>Integer</i>
	 * 
	 * @param array Mảng kiểu int
	 * @return Mảng kiểu Integer
	 */
	public static Integer[] toArray(int[] array) {
		Integer[] newArray = new Integer[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Integer(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Integer</i> sang kiểu <i>int</i>
	 * 
	 * @param array Mảng kiểu Integer
	 * @return Mảng kiểu int
	 */
	public static int[] toArray(Integer[] array) {
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].intValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>long</i> sang kiểu <i>Long</i>
	 * 
	 * @param array Mảng kiểu long
	 * @return Mảng kiểu Long
	 */
	public static Long[] toArray(long[] array) {
		Long[] newArray = new Long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Long(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Long</i> sang kiểu <i>long</i>
	 * 
	 * @param array Mảng kiểu Long
	 * @return Mảng kiểu long
	 */
	public static long[] toArray(Long[] array) {
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].longValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>short</i> sang kiểu <i>Short</i>
	 * 
	 * @param array Mảng kiểu short
	 * @return Mảng kiểu Short
	 */
	public static Short[] toArray(short[] array) {
		Short[] newArray = new Short[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = new Short(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển đổi một mảng kiểu <i>Short</i> sang kiểu <i>short</i>
	 * 
	 * @param array Mảng kiểu Short
	 * @return Mảng kiểu short
	 */
	public static short[] toArray(Short[] array) {
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i].shortValue();
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>Object</i> sang kiểu <i>Long</i>
	 * 
	 * @param array Mảng kiểu <i>Object</i>
	 * @return Mảng kiểu <i>Long</i>
	 */
	public static Long[] toLongArray(Object[] array) {
		Long[] newArray = new Long[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = (Long) array[i];
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>boolean</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>boolean</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(boolean[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>array</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>array</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(byte[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>char</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>char</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(char[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>Date</i> sang kiểu <i>String</i>
	 * 
	 * @param array      Mảng kiểu <i>Date</i>
	 * @param dateFormat Định dạng ngày tháng
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(Date[] array, DateFormat dateFormat) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = dateFormat.format(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>double</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>double</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(double[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>float</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>float</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(float[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>int</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>int</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(int[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>long</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>long</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(long[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>Object</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>Object</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(Object[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}

	/**
	 * Hàm chuyển mảng kiểu <i>short</i> sang kiểu <i>String</i>
	 * 
	 * @param array Mảng kiểu <i>short</i>
	 * @return Mảng kiểu <i>String</i>
	 */
	public static String[] toStringArray(short[] array) {
		String[] newArray = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}

		return newArray;
	}
}

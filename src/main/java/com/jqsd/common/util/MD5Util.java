package com.jqsd.common.util;


import java.io.UnsupportedEncodingException;

import com.jqsd.common.constant.CommonConstant;

/**
 * Md5工具
 */
public class MD5Util {

    /**
 	 * md5-与jquery相同
 	 * @param src 字符串
 	 * @return 16进制整数字符串格式
 	 */
 	public static String getMD5(String src) {
        byte[] source = null;
        try {
            source = src.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String s = null;
 		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
 		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
 				'e', 'f' };
 		try {
 			java.security.MessageDigest md = java.security.MessageDigest
 					.getInstance("MD5");
 			md.update(source);
 			// MD5 的计算结果是一个 128 位的长度整数，
 			byte tmp[] = md.digest();

 			// 用字节表示就是 16 个字节
 			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
 			// 所以表示成 16 进制需要 32 个字符
 			int k = 0; // 表示转换结果中对应的字符位置
 			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
 											// 转换成 16 进制字符的转换
 				byte byte0 = tmp[i]; // 取第 i 个字节
 				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
 				// >>> 为逻辑右移（即无符号右移），将符号位一起右移

 				// 取字节中低 4 位的数字转换
 				str[k++] = hexDigits[byte0 & 0xf];
 			}
 			s = new String(str); // 换后的结果转换为字符串

 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		return s;
 	}
 	
 	/**
 	 * md5密码
 	 * @param pwd 已md5加密密码
 	 * @return 16进制整数字符串格式
 	 */
 	public static String getMD5Pwd(String pwd) {
		return getMD5((CommonConstant.PWD_MD5_KEY + pwd));
 	}

    public static String getSignMD5(String sign) {
        return getMD5((CommonConstant.API_MD5_KEY + sign));
    }

 	public static String getDefaultPwd(){
        return getMD5Pwd(getMD5("123456"));
    }

    public static void main(String[] args) {
        System.out.println(getDefaultPwd());
    }
}

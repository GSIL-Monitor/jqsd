package com.jqsd.common.util;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.jqsd.common.model.ScheduleJob;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * String工具
 */
public class StrUtil {

	private static final Logger LOG = LoggerFactory.getLogger(StrUtil.class);
			
    /**
 	 * 获取UUID 
 	 * @return UUID
 	 */
 	public static String getUUID() {
 		return UUID.randomUUID().toString().replaceAll("-", "");
 	}
 	
 	/**
 	 * 字符串转数字
 	 * @param str 字符串
 	 * @param def 默认值
 	 * @return 数字
 	 */
 	public static Integer getInteger(String str, Integer def){
 		try {
 			if (StrUtil.isBlank(str)){
 				LOG.warn("字符串为空:" + str);
 				return def;
 			}
			return Integer.parseInt(str);
		} catch (Exception e) {
			LOG.error("字符串转数字异常:" + e.toString());
		}
 		return def;
 	}
 	
	/**
 	 * 字符串转小写
 	 * @param str 字符串
 	 * @return 转小写
 	 */
 	public static String toLowerCase(String str){
 		if (isBlank(str)){return str;}
 		return str.toLowerCase();
 	}
 	/**
 	 * 字符串转大写
 	 * @param str 字符串
 	 * @return 转大写
 	 */
 	public static String toUpperCase(String str){
 		if (isBlank(str)){return str;}
 		return str.toUpperCase();
 	}
	/**
     * 清除无关的不可见空白字符
     * @param str
     * @param includingBlank 是否包括移除文本内部的空白字符
     * @return
     */
    public  static String cleanInvisibleChar(String str, boolean includingBlank) {
        if (str != null) {
            str = StringUtils.remove(str, (char) 160);
            if (includingBlank) {
                //普通空格
                str = StringUtils.remove(str, " ");
                //全角空格
                str = StringUtils.remove(str, (char) 12288);
            }
            str = StringUtils.remove(str, "\r");
            str = StringUtils.remove(str, "\n");
            str = StringUtils.remove(str, "\t");
            str = StringUtils.remove(str, "\\s*");
            str = StringUtils.remove(str, "◆");
            str = str.trim();
        }
        return str;
    }
    /**
     * 
     * @param str
     * @return cleanInvisibleChar(str,false);
     */
    public static String cleanInvisibleChar(String str){
    	return cleanInvisibleChar(str,false);
    }
 	/**
 	 * 为空
 	 * @param str 字符串
 	 * @return boolean
 	 */
 	public static boolean isEmpty(String str){
 	    return (str == null) || (str.length() == 0);
 	}

 	/**
 	 * 不为空
 	 * @param str 字符串
 	 * @return boolean
 	 */
    public static boolean isNotEmpty(String str){
 	    return !isEmpty(str);
 	}

    /**
 	 * 为空或空白
 	 * @param str 字符串
 	 * @return boolean
 	 */
 	public static boolean isBlank(String str){
 	    int strLen;
 	    if ((str == null) || ((strLen = str.length()) == 0)){
 	        return true;
 	    }
 	    for (int i = 0; i < strLen; i++){
 	      if (!Character.isWhitespace(str.charAt(i))){
 	        return false;
 	      }
 	    }
 	    return true;
 	}

 	/**
 	 * 不为空或空白
 	 * @param str 字符串
 	 * @return boolean
 	 */
 	public static boolean isNotBlank(String str){
 	    return !isBlank(str);
 	}
 	
 	/**
 	 * 不为空或空白
 	 * @param strs 字符串
 	 * @return boolean
 	 */
 	public static boolean isBlanks(String... strs){
 		boolean res = false;
 	    if (strs != null && strs.length > 0){
 	    	for (int i = 0; i < strs.length; i++) {
				if (isBlank(strs[i])){
					res = true;
					break;
				}
			}
 	    }
 	    return res;
 	}
 	
 	/**
 	 * 判断是否是金额，2位小数
 	 * @param str 字符串
 	 * @return 是否
 	 */
 	public static boolean isMoney(String str){
		return Pattern.matches("^([0-9]+)([\\.]([0-9]{1,2}))?$", str);
 	}
 	
    /**
 	 * 格式化电话为*号-$1***$2
 	 * @param phone 电话
 	 * @return 格式化后电话
 	 */
 	public static String formatPhone(String phone) {
 		if (isBlank(phone)){return phone;}
 		return phone.replaceAll("(\\S{7})\\d{3}(\\S*)", "$1***$2");
 	}
 	
    /**
 	 * 格式化手机为*号-$1****$2
 	 * @param mobile 手机
 	 * @return 格式化后手机
 	 */
 	public static String formatMobile(String mobile) {
 		if (isBlank(mobile)){return mobile;}
 		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
 	}
 	
 	/**
 	 * 格式化邮箱为*号-$1****$2
 	 * @param email 邮箱
 	 * @return 格式化后邮箱
 	 */
 	public static String formatEmail(String email) {
 		if (isBlank(email)){return email;}
 		return email.replaceAll("(\\S{2})\\S{0,4}(\\S*@\\S*)", "$1****$2");
 	}
 	
 	/**
 	 * 格式化身份证为*号-$1****$2
 	 * @param cardNo 身份证
 	 * @return 格式化后身份证
 	 */
 	public static String formatCardNo(String cardNo) {
 		if (isBlank(cardNo)){return cardNo;}
 		return cardNo.replaceAll("(\\d{6})\\d{8}(\\d{4})", "$1****$2");
 	}
 	
 	/**
 	 * 格式化银行卡号-返回后4位
 	 * @param card 身份证
 	 * @return 格式化后银行卡号
 	 */
 	public static String formatBankCard(String card) {
 		if (isBlank(card)){return card;}
 		return card.replaceAll("(\\d*)(\\d{4})", "$2");
 	}
 	
 	/**
 	 * 去除最后逗号
 	 * @param str 字符串
 	 * @return 字符串
 	 */
 	public static String replaceEndCom(String str) {
 		return str.replaceAll(",$", "");
 	}
 	
 	/**
 	 * 判断null转空字符串
 	 * @param str 字符串
 	 * @return 空字符串或str
 	 */
 	public static String nullToBlank(String str){
 		if (isEmpty(str)){return "";}
 		return str;
 	}
 	
 	/**
 	 * 格式化字符串
 	 * @param str 字符串
 	 * @param arg 参数-{0}占位
 	 * @return 格式化后字符串
 	 */
 	public static String formatString(String str, Object[] arg) {
 		return MessageFormat.format(str, arg);
 	}
 	
 	/**
 	 * 是否包含字符串
 	 * @param str 字符串
 	 * @param strArr 字符串列表,逗号分割
 	 * @return 是否包含
 	 */
 	public static boolean isInStrArr(String str, String strArr) {
 		if (str == null || strArr == null){
 			return false;
 		}
 		
 		boolean isEq = false;
 		for(String arr : strArr.split(",")){
 			if (str.toLowerCase().equals(arr.toLowerCase())){
 				isEq = true;
 				break;
 			}
 		}
 		return isEq;
 	}
 	
 	/**
 	 * 获取6位随机数字
 	 * @return 6位随机数字
 	 */
 	public static String getSixRandomNum(){
 		return String.valueOf((int)((Math.random()*9 + 1) * 100000));
 	}
 	
 	/**
 	 * 是否在数字区间
 	 * @param num 数字
 	 * @param intArr 数字区间列表,逗号分割,区间用-分割
 	 * @return 是否在区间
 	 */
 	public static boolean isInIntArr(int num, String intArr) {
 		if (intArr == null){
 			return false;
 		}
 		
 		boolean isEq = false;
 		String [] arrs = null;
 		for(String arr : intArr.split(",")){
 			arrs = arr.split("-");
 			if (arrs.length == 2){
 				if (num >= getInteger(arrs[0], 0) && num <= getInteger(arrs[1], 0)){
 					isEq = true;
 	 				break;
 				}
 			}else{
 				if (num == getInteger(arr, 0)){
 	 				isEq = true;
 	 				break;
 	 			}
 			}
 		}
 		return isEq;
 	}
 	
 	public static String array2str(String[] array, String delimiter){
 		if (array != null && array.length > 0) {
 			StringBuilder str = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				str.append(array[i]);
				if (i < array.length-1) {
					str.append(delimiter);
				}
			}
			return str.toString();
		}
 		return "";
 	}
 	

 	//浮点型判断          整数或小数返回true
    public static   boolean isDecimal(String str) {
     if(str==null || "".equals(str))
      return false;  
     Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?");
     return pattern.matcher(str).matches();
    }

	/**
	 * 组装schedule cron
	 * @param job
	 * @return
	 */
	public static String getCron(ScheduleJob job){
 		return Joiner.on(" ").join("0",job.getMin(),job.getHour(),job.getDay(),job.getMon(),job.getWeek());
	}
}

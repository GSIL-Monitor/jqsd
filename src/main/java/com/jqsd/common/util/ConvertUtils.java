package com.jqsd.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sam on 16-8-3.
 */
public class ConvertUtils {
    public static final Logger LOG = LoggerFactory
            .getLogger(ConvertUtils.class);

    public static Map formJson(String json) {
        return new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static List<Map<String, Object>> formJsonList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }

    public static List formJson2List(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
    }


    public static String convertObjToStr(Object o) {
        if (o instanceof Number) {
            Number d = (Number) o;
            if (d.doubleValue() == d.longValue()) {
                return d.longValue() + "";
            } else {
                return d.toString();
            }
        } else if (o instanceof String) {
            return o.toString();
        }
        return "";
    }

    public static float convertObjToFloat(Object o) {
        if (o instanceof Number) {
            return ((Number)o).floatValue();
        }
        return -1;
    }

    public static double convertObjToDouble(Object o) {
        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }if(o instanceof String){
            return Double.parseDouble(o+"");
        }
        return -1;
    }

    public static BigDecimal convertObjToBigDecimal(Object o, int scale) {
        BigDecimal mData = new BigDecimal(o.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return mData;
    }

    public static int convertObjToInt(Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue();
        } else if (o instanceof String) {
            return Integer.parseInt(((String) o));
        } else if (o instanceof Boolean) {
            return ((Boolean) o) ? 1 : 0;
        }
        return -1;
    }

    public static String convertObjToDate(Object o) {
        String date = (String) o;
        if (StrUtil.isBlank(date)) {
            return null;
        } else {
            return date.replace("T", " ").replace("年", "-").replace("月", "-").replace("日", "-");
        }
    }

    public static String replaceBlank(Object str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s|\t|\r|\n");
            Matcher m = p.matcher((String) str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    public static long convertObjToLong(Object o) {
        if (o instanceof Number) {
            return ((Number)o).longValue();
        } else if (o instanceof String) {
            String num = (String) o;
            if (num.endsWith("万")) {
                String regEx = "[^0-9|.]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(num);
                try {
                    String n = m.replaceAll("").trim();
                    return ((Float) (Float.parseFloat(n) * 10000)).intValue();
                } catch (NumberFormatException e) {
                    return 10000;
                }
            }
            return Long.parseLong(num);
        }
        return -1;
    }

}

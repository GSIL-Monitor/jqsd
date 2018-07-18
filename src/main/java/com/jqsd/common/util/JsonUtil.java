package com.jqsd.common.util;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jfinal.i18n.I18n;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jqsd.common.model.base.BaseDbConfig;

/**
 * Json工具
 */
public class JsonUtil {

    public interface STATE {
        /**
         * 成功状态-200
         */
        Short SUCC = 200;
        /**
         * 失败状态-500
         */
        Short FAIL = 500;
    }

    /**
     * 获取返回信息
     *
     * @param msg 消息
     * @return Map
     */
    public static Map<String, Object> getSucc(String msg) {
        return getResult(STATE.SUCC, msg, null);
    }


    /**
     * 获取返回信息
     *
     * @param msg
     * @param data
     * @return
     */
    public static Map<String, Object> getSucc(String msg, Object data) {
        return getResult(STATE.SUCC, msg, data);
    }
    public static Map<String, Object> getData( Object data) {
        return getResult(STATE.SUCC, null, data);
    }

    public static Map<String,Object> getFile(String fileName){
        Map map = Maps.newHashMap();
        map.put("file",fileName);
        map.put("path", FileUtil.getAbsDownLoadPath());
        return getResult(STATE.SUCC, null, map);
    }


    /**
     * 获取返回信息
     *
     * @param msg
     * @return
     */
    public static Map<String, Object> getFail(String msg) {
        return getResult(STATE.FAIL, msg, null);
    }

    /**
     * 获取返回信息
     *
     * @param state 状态
     * @param msg   消息
     * @return Map
     */
    private static Map<String, Object> getResult(Short state, String msg, Object data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("state", state);
        if (data != null) {
            map.put("data", data);
        }
        if(StrUtil.isNotBlank(msg)){
            map.put("msg", msg);
        }
        return map;
    }

    public static Map<String,Object> getPage(){
        return getPage(null);
    }

    public static Map<String,Object> getPage(Page page){
        Map map = Maps.newHashMap();
        map.put("code","0");
        map.put("count",page==null?0:page.getTotalRow());
        map.put("data",page==null?new ArrayList():page.getList());
        return map;
    }


    public static Map<String, Object> getSucc() {
        return getResult(STATE.SUCC, "保存成功", null);
    }
    public static Map<String, Object> getFail() {
        return getResult(STATE.FAIL, "保存失败", null);
    }
    public static Map<String, Object> getDelSucc() {
        return getResult(STATE.SUCC, "删除成功", null);
    }
    public static Map<String, Object> getDelFail() {
        return getResult(STATE.FAIL, "删除失败", null);
    }

}

package com.jqsd.core.job;

import com.jfinal.core.JFinal;

public class Test {
    /**
     *  IDEA的启动方式
     * @param args
     */
    public static void main(String[] args) {
        // IDEA 下的启动方式
        JFinal.start("src/main/webapp", 8080, "/");
    }
}

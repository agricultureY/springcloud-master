package com.ycn.redistool.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 脚本工具类
 *
 * @author ycn
 * @package com.ycn.redistool.util
 * @ClassName ScriptUtil
 * @Date 2018/7/20 21:40
 */
public class ScriptUtil {

    public static String getScript(String path) {
        StringBuilder sb = new StringBuilder();
        InputStream stream = ScriptUtil.class.getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        try {
            String str = "";
            while ((str = br.readLine()) != null)
                sb.append(str).append(System.lineSeparator());
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        return sb.toString();
    }
}

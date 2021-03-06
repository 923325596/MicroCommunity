package com.java110.web.core;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 静态资源文件加载器
 * Created by wuxw on 2019/3/18.
 */
public class VueComponentTemplate extends PackageScanner {

    /**
     * 默认扫描路径
     */
    public final static String DEFAULT_COMPONENT_PACKAGE_PATH = "components";

    /**
     * js 文件
     */
    public final static String COMPONENT_JS = "js";

    /**
     * css 文件
     */
    public final static String COMPONENT_CSS = "css";

    /**
     * html 文件
     */
    public final static String COMPONENT_HTML = "html";


    /**
     * HTML 文件缓存器
     */
    private final static Map<String, String> componentTemplate = new HashMap<>();


    /**
     * 初始化 组件信息
     */
    public static void initComponent(String scanPath) {
        VueComponentTemplate vueComponentTemplate = new VueComponentTemplate();
        vueComponentTemplate.packageScanner(scanPath, COMPONENT_JS);
        vueComponentTemplate.packageScanner(scanPath, COMPONENT_HTML);
        vueComponentTemplate.packageScanner(scanPath, COMPONENT_CSS);
    }


    /**
     * 根据组件编码查询模板
     *
     * @param componentCode
     * @return
     */
    public static String findTemplateByComponentCode(String componentCode) {
        if (componentTemplate.containsKey(componentCode)) {
            return componentTemplate.get(componentCode);
        }

        return null;
    }


    /**
     * 处理资源
     *
     * @param filePath
     */
    protected void handleResource(String filePath) {
        Reader reader = null;
        String sb = "";
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            reader = new InputStreamReader(inputStream, "UTF-8");
            int tempChar;
            StringBuffer b = new StringBuffer();
            while ((tempChar = reader.read()) != -1) {
                b.append((char) tempChar);
            }
            sb = b.toString();
            if (!StringUtils.isEmpty(sb)) {
                componentTemplate.put(filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length()), sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

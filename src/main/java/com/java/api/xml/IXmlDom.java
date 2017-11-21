package com.java.api.xml;

public interface IXmlDom {
    /**
     * 建立XML文档
     * @param fileName 文件全路径名称
     */
    public void createXml(String fileName) throws Exception;
    /**
     * 解析XML文档
     * @param fileName 文件全路径名称
     */
    public void parseXml(String fileName) throws Exception;
}

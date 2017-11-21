package com.java.api.xml.dom4j;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.java.api.xml.IXmlDom;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author hongliang.dinghl
 * Dom4j 生成XML文档与解析XML文档
 */
public class Dom4jDemo implements IXmlDom {
    public static void main(String[] args) {
        String xmlFile = "result/dom4j_demo.xml";
        Dom4jDemo demo = new Dom4jDemo();
        demo.createXml(xmlFile);
        demo.parseXml(xmlFile);
    }

    @Override
    public void createXml(String fileName) {
        // create document
        Document document = DocumentHelper.createDocument();
        Element employees=document.addElement("employees");
        Element employee=employees.addElement("employee");
        Element name= employee.addElement("name");
        name.setText("LiMing");
        Element sex=employee.addElement("sex");
        sex.setText("male");
        Element age=employee.addElement("age");
        age.setText("29");

        employee=employees.addElement("employee");
        name= employee.addElement("name");
        name.setText("XiaoFang");
        sex=employee.addElement("sex");
        sex.setText("female");
        age=employee.addElement("age");
        age.setText("28");
        // write xml file
        try {
            Writer fileWriter=new FileWriter(fileName);
            XMLWriter xmlWriter=new XMLWriter(fileWriter);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void parseXml(String fileName) {
        File inputXml=new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees=document.getRootElement();
            printNodes("", employees);
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printNodes(String blank, Element node){
        //print current node
        System.out.println(blank + node.getName() + ":" + node.getTextTrim());
        List<Attribute> listAttr=node.attributes();
        for(Attribute attr:listAttr){
            System.out.println(blank + attr.getName() + ":" + attr.getValue());
        }

        //iterate over children nodes
        List<Element> listElement=node.elements();
        for(Element e:listElement){
            this.printNodes(blank + "    ", e);
        }
    }
}

package com.java.api.xml.jdom;

import com.java.api.xml.IXmlDom;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * JDOM 生成与解析XML文档
 *
 * JDOM是一个开源项目，它基于树形结构，利用纯Java的技术对XML文档实现解析、生成、序列化及多种操作。
 *
 * JDom的基本元素：
 *  1. Document: xml文档对象
 *  2. Element: xml中的元素
 *  3. Attribute: xml中元素的属性
 */
public class JDomDemo implements IXmlDom {
    private final String INDENT = "    ";

    public static void main(String[] args) throws Exception {
        String fileName = "result/jdom_demo.xml";
        JDomDemo demo = new JDomDemo();
        demo.createXml(fileName);
        demo.parseXml(fileName);
        demo.parseXml(new URL("http://www.cafeconleche.org/"));
    }

    @Override
    public void createXml(String fileName) throws Exception {
        // crt root element;
        Element root = new Element("list");
        // set root for document；
        Document doc = new Document(root);
        for (int i = 0; i < 5; i++) {
            // crt children elements;
            Element elements = new Element("company");
            elements.setAttribute("id", "" + i);
            elements.addContent(new Element("name").setText("name" + i));
            elements.addContent(new Element("email").setText("name" + i    + "@163.com"));
            root.addContent(elements);
        }
        // output
        Format f = Format.getPrettyFormat();
        f.setIndent(INDENT);
        XMLOutputter out = new XMLOutputter(f);
        out.output(doc, new FileOutputStream(fileName));
    }

    @Override
    public void parseXml(String fileName) throws Exception {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new FileInputStream(fileName));
        Element root = doc.getRootElement();
        printElement("", root);
    }

    public void parseXml(URL url) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document doc = parser.build(url);
        Element root = doc.getRootElement();
        printElement("", root);
    }

    private void printElement(String blank, Element e) {
        List<Element> children = e.getChildren();
        if (children.size() == 0) {
            System.out.print("\n" + blank + e.getName() + ":" + e.getTextTrim());
        } else {
            System.out.print("\n" + blank + e.getName() + ":");
            List<Attribute> attrs = e.getAttributes();
            for(int i=0; i<attrs.size(); i++) {
                Attribute attr = attrs.get(i);
                if (i<attrs.size()-1) {
                    System.out.print(attr.getName() + "=" + attr.getValue() + ",");
                } else {
                    System.out.print(attr.getName() + "=" + attr.getValue());
                }
            }
            for (Element c: children) {
                printElement("    " + blank, c);
            }
        }
    }
}
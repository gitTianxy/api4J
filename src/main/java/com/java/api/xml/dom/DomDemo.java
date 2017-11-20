package com.java.api.xml.dom;

import com.java.api.xml.IXmlDom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * DOM方式生成与解析XML文档
 * <p>
 * 解析器读入整个文档，然后构建一个驻留内存的树结构，然后代码就可以使用 DOM 接口来操作这个树结构。
 * <p>
 * 优点：整个文档树在内存中，便于操作；支持删除、修改、重新排列等多种功能；
 * 缺点：将整个文档调入内存（包括无用的节点），浪费时间和空间；
 * 使用场合：一旦解析了文档还需多次访问这些数据；硬件资源充足（内存、CPU）。
 */
public class DomDemo implements IXmlDom {
    private DocumentBuilder builder;

    public static void main(String[] args) {
        DomDemo demo = new DomDemo();
        demo.init();
        String xmlFile = "result/dom_demo.xml";
        demo.createXml(xmlFile);
        demo.parserXml(xmlFile);
    }

    public void init() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            this.builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * OPERATION STEPS:
     * I. build dom
     * II. transform dom to string
     * <p>
     * METHODS
     * 1. Document:
     * a. createElement(tagName)
     * b. createTextNode(data)
     * 2. Element:
     * a. appendChild(element)
     * 3. Transformer: ...
     *
     * @param fileName 文件全路径名称
     */
    @Override
    public void createXml(String fileName) {
        /**
         * I. build dom
         */
        Document doc = this.builder.newDocument();

        Element root = doc.createElement("employees");
        doc.appendChild(root);

        Element employee = doc.createElement("employee");
        // 1.name
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode("Zhangsan"));
        employee.appendChild(name);
        // 2.sex
        Element sex = doc.createElement("sex");
        sex.appendChild(doc.createTextNode("male"));
        employee.appendChild(sex);
        // 3.age
        Element age = doc.createElement("age");
        age.appendChild(doc.createTextNode("29"));
        employee.appendChild(age);
        //
        root.appendChild(employee);

        employee = doc.createElement("employee");
        // 1.name
        name = doc.createElement("name");
        name.appendChild(doc.createTextNode("WangFang"));
        employee.appendChild(name);
        // 2.sex
        sex = doc.createElement("sex");
        sex.appendChild(doc.createTextNode("female"));
        employee.appendChild(sex);
        // 3.age
        age = doc.createElement("age");
        age.appendChild(doc.createTextNode("28"));
        employee.appendChild(age);
        //
        root.appendChild(employee);
        /**
         * II. transform dom to string
         */
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * OPERATION STEPS:
     * 1. parse string 2 document
     * 2. iterate over nodes of document
     *
     * @param fileName 文件全路径名称
     */
    @Override
    public void parserXml(String fileName) {
        try {
            Document document = this.builder.parse(fileName);
            printNode("", document);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printNode(String blank, Node n) {
        System.out.print("\n" + blank + n.getNodeName() + ":");

        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.hasChildNodes()) {
                printNode("    " + blank, child);
                continue;
            }
            if ("\n".equals(child.getNodeValue()) || "".equals(child.getNodeValue())) {
                continue;
            }
            if ("#text".equals(child.getNodeName())) {
                System.out.print(child.getNodeValue());
            }
        }
    }
}

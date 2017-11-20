package com.java.api.xml.dom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.java.api.xml.IXmlDom;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX文档解析
 * <p>
 * 为解决DOM的问题，出现了SAX。SAX ，事件驱动。当解析器发现元素开始、元素结束、文本、文档的开始或结束等时，发送事件，程序员编写响应这些事件的代码，保存数据。
 * 优点：
 *      不用事先调入整个文档，占用资源少；
 *      SAX解析器代码比DOM解析器代码小，适于Applet，下载。
 * 缺点：
 *      不是持久的；事件过后，若没保存数据，那么数据就丢了；
 *      无状态性；从事件中只能得到文本，但不知该文本属于哪个元素；
 * 使用场合：Applet;只需XML文档的少量内容，很少回头访问；机器内存少
 */
public class SaxDemo implements IXmlDom {
    public void createXml(String fileName) {
        System.out.println("<<" + fileName + ">>");
    }

    public void parserXml(String fileName) {
        SAXParserFactory saxfac = SAXParserFactory.newInstance();
        try {
            SAXParser saxparser = saxfac.newSAXParser();
            InputStream is = new FileInputStream(fileName);
            saxparser.parse(is, new MySAXHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MySAXHandler extends DefaultHandler {
    boolean hasAttribute = false;
    Attributes attributes = null;

    public void startDocument() throws SAXException {
        System.out.println("文档开始打印了");
    }

    public void endDocument() throws SAXException {
        System.out.println("文档打印结束了");
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.equals("employees")) {
            return;
        }
        if (qName.equals("employee")) {
            System.out.println(qName);
        }
        if (attributes.getLength() > 0) {
            this.attributes = attributes;
            this.hasAttribute = true;
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (hasAttribute && (attributes != null)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.getQName(0)
                        + attributes.getValue(0));
            }
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        System.out.println(new String(ch, start, length));
    }
}

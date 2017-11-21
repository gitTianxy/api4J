# xml api
* review
* dom(原生jdk api)
* dom4j
* jdom
* others

---
## review
### xml简介
* XML现在已经成为一种通用的数据交换格式: 它的平台无关性,语言无关性,系统无关性,给数据集成与交互带来了极大的方便。

### xml相关语法内容
* DOM(Document Object Model)
* DTD(Document Type Definition)
* SAX(Simple API for XML)
* XSD(Xml Schema Definition)
* XSLT(Extensible Stylesheet Language Transformations)

### xml的解析方式有两种
1. SAX: 基于事件流的解析
2. DOM: 基于XML文档树结构的解析

## dom及原生api
* DOM是一个接口定义语言(IDL), 它是在不同语言实现中的一个最低的通用标准，而不是为Java特别设计的。
* Java中的原生DOM API沿袭了XML规范，在XML中，每件东西都是一个结点，因此在DOM中找到的几乎每件东西都基于Node接口。
* 优点：就多态性来讲，它是优秀的，
* 缺点：它在Java语言中的应用是困难而且不便的，其中从Node向叶类型作显式向下类型转换会导致代码的冗长和难以理解。

### JAXP(Java API for XMLProcessing)
- org.w3c.dom ，W3C 推荐的用于XML标准规划文档对象模型的 Java 工具
- org.xml.sax，用于对 XML 进行语法分析的事件驱动的简单 API
- javax.xml.parsers ，工厂化工具，允许应用程序开发人员获得并配置特殊的语法分析器工具


## jdom
* JDOM是两位著名的Java 开发人员兼作者，Brett Mclaughlin 和 Jason Hunter 的创作成果，
2000年初在类似于Apache协议的许可下，JDOM作为一个开放源代码项目正式开始研发。
它已成长为包含来自广泛的 Java 开发人员的投稿、集中反馈及错误修复的系统，
并致力于建立一个完整的基于 Java 平台的解决方案，通过 Java 代码来访问、操作并输出 XML 数据。
* 相较于dom，JDOM直接为JAVA编程服务，在使用设计上尽可能地隐藏原来使用XML过程中的复杂性。
* 它利用更为强有力的JAVA语言的诸多特性（方法重载、集合概念以及映射），把SAX和DOM的功能有效地结合起来。
* builder：
    - DOMBuilder：dom方式操作xml对象；适合小文件解析
    - SAXBuilder(首选)：sax方式操作xml对象；适合文件以及依据网路url抓取的xml资源
* outputter:
    - XMLOutputter(首选)
    - SAXOutputter
    - DOMOutputter

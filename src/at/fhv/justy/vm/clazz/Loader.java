package at.fhv.justy.vm.clazz;

import at.fhv.justy.vm.CodeContainer;
import at.fhv.justy.vm.constant.ConstantPool;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johannes on 20.06.14.
 */
public class Loader {

    private HashMap<String, Node> constants = new HashMap<>();

    private HashMap<Integer, Integer> reAllocate = new HashMap<>();

    public Clazz load(String clazzFile, CodeContainer codeContainer, ConstantPool constantPool) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        File fXmlFile = new File(clazzFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList constantList = doc.getElementsByTagName("constant_pool").item(0).getChildNodes();

        for (int i = 0; i < constantList.getLength(); i++) {
            Node constant = constantList.item(i);
            if (constant.getNodeType() != 3) {
                constants.put(constant.getAttributes().getNamedItem("id").getNodeValue(), constant);

                String type = "";
                byte[] value = null;
                switch (constant.getNodeName()) {
                    case "constant_integer":
                        type = "I";
                        value = ByteBuffer.allocate(4).putInt(Integer.parseInt(constant.getChildNodes().item(1).getTextContent())).array();
                        break;
                    default:
                        continue;
                }

                int id = Integer.parseInt(constant.getAttributes().getNamedItem("id").getNodeValue());
                int realId = constantPool.add(value, type);
                reAllocate.put(id, realId);

            }
        }

        List<Field> fields = new ArrayList<>();
        String expression = "/classfile/field_info/field";
        System.out.println(expression);
        NodeList fieldInfoList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < fieldInfoList.getLength(); i++) {
            Node field = fieldInfoList.item(i);
            if (field.getNodeType() != 3) {
                String nameIndex = ((Node) xPath.compile("name_index").evaluate(field, XPathConstants.NODE)).getTextContent();
                String descriptorIndex = ((Node) xPath.compile("descriptor_index").evaluate(field, XPathConstants.NODE)).getTextContent();
                String name = getTextContent(nameIndex);
                String type = getTextContent(descriptorIndex);

                fields.add(new Field(name, type));
            }
        }

        List<Method> methods = new ArrayList<>();
        NodeList methodInfoList = (NodeList) xPath.compile("/classfile/method_info/method").evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < methodInfoList.getLength(); i++) {
            Node method = methodInfoList.item(i);
            if (method.getNodeType() != 3) {
                methods.add(loadMethod(xPath, method, codeContainer));
            }
        }
        String nameIndex = ((Node) xPath.compile("/classfile/this_class").evaluate(doc, XPathConstants.NODE)).getTextContent();
        String name = constants.get(nameIndex).getChildNodes().item(1).getTextContent();

        return new Clazz(name, methods.toArray(new Method[]{}), fields.toArray(new Field[]{}));
    }

    private Method loadMethod(XPath xPath, Node method, CodeContainer codeContainer) throws XPathExpressionException {
        String nameIndex = ((Node) xPath.compile("name_index").evaluate(method, XPathConstants.NODE)).getTextContent();
        String descriptorIndex = ((Node) xPath.compile("descriptor_index").evaluate(method, XPathConstants.NODE)).getTextContent();

        String code = ((Node) xPath.compile("attribute_info/attribute/info/code").evaluate(method, XPathConstants.NODE)).getTextContent();
        String stackCount = ((Node) xPath.compile("attribute_info/attribute/info/max_stack").evaluate(method, XPathConstants.NODE)).getTextContent();
        String localsCount = ((Node) xPath.compile("attribute_info/attribute/info/max_locals").evaluate(method, XPathConstants.NODE)).getTextContent();

        String name = getTextContent(nameIndex);
        String type = getTextContent(descriptorIndex);

        String[] codeLines = code.split("\n");

        for (int x = 0; x < codeLines.length; x++) {
            if (codeLines[x].trim().startsWith("LDC_W")) {
                int id = Integer.parseInt(codeLines[x].trim().split(" ")[1]);
                if (this.reAllocate.containsKey(id)) {
                    codeLines[x] = codeLines[x].replace(codeLines[x].trim().split(" ")[1], this.reAllocate.get(id).toString());
                }
            }
        }

        int start = codeContainer.append(codeLines);
        int length = codeLines.length;

        return new Method(name, type, Integer.getInteger(stackCount), Integer.getInteger(localsCount), code, start, length);
    }

    private String getTextContent(String index) {
        return constants.get(index).getChildNodes().item(1).getTextContent();
    }
}

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Configuration {

    private boolean load;
    private String loadName;
    private String loadFormat;

    private boolean save;
    private String saveName;
    private String safeFormat;

    private boolean log;
    private String logName;

    public boolean isLoad() {
        return load;
    }

    public String getLoadName() {
        return loadName;
    }

    public String getLoadFormat() {
        return loadFormat;
    }

    public boolean isSave() {
        return save;
    }

    public String getSaveName() {
        return saveName;
    }

    public String getSafeFormat() {
        return safeFormat;
    }

    public boolean isLog() {
        return log;
    }

    public String getLogName() {
        return logName;
    }

    public void loadConfig() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));
            readConfig(doc);
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void readConfig(Document doc) {
        // считываем ноду load;
        NodeList nodeList = doc.getElementsByTagName("load");
        Element nodeSet = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeSet.getNodeType()) {
            load = Boolean.parseBoolean(nodeSet.getElementsByTagName("enabled").item(0).getTextContent());
            loadName = nodeSet.getElementsByTagName("fileName").item(0).getTextContent();
            loadFormat = nodeSet.getElementsByTagName("format").item(0).getTextContent();
        }

        // считываем ноду save;
        nodeList = doc.getElementsByTagName("save");
        nodeSet = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeSet.getNodeType()) {
            save = Boolean.parseBoolean(nodeSet.getElementsByTagName("enabled").item(0).getTextContent());
            saveName = nodeSet.getElementsByTagName("fileName").item(0).getTextContent();
            safeFormat = nodeSet.getElementsByTagName("format").item(0).getTextContent();
        }

        // считываем ноду log;
        nodeList = doc.getElementsByTagName("log");
        nodeSet = (Element) nodeList.item(0);
        if (Node.ELEMENT_NODE == nodeSet.getNodeType()) {
            log = Boolean.parseBoolean(nodeSet.getElementsByTagName("enabled").item(0).getTextContent());
            logName = nodeSet.getElementsByTagName("fileName").item(0).getTextContent();
        }
    }
}
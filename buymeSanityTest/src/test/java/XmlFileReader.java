import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
public class XmlFileReader {

//    set xml constants
//    browser key for XML parser
    private static final String BROWSER_KEY = "browserType";
//    URL key for XML parser
    private static final  String URL_KEY = "URL";
//         set XML file path
    private static final String PATH_XML = Constants.MAIN_PATH + "Source\\buymeConfig.xml";

//    read xml data from filePath, extract data by keyName

    public static String getData(String filePath, String keyName) throws ParserConfigurationException, IOException, SAXException {
        File configXmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configXmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        assert doc != null;
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

//    get driver type from the config file
    public static String getBrowserType(){
    String browserType = "unknown";
        try {
            browserType  = XmlFileReader.getData(PATH_XML,BROWSER_KEY);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        };
        return browserType;
    }

//      get landing URL  from the config file
    public static String getLandingURL(){
        String url = "";
        try {
            url = XmlFileReader.getData(PATH_XML,URL_KEY);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return url;
    }

    }




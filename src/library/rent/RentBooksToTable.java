package library.rent;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.defaults.XMLDefaults;
import library.objects.RentData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class RentBooksToTable
{
    public ObservableList<RentData> readXML()
    {
        ObservableList<RentData> result = FXCollections.observableArrayList();
        try
        {
            /** Application **/
            File fXmlFile = new File(XMLDefaults.RENT_DATA_PATH_XML);

            /** Testing **/
            //File fXmlFile = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(XMLDefaults.RENT);

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;

                    String p_name = eElement.getElementsByTagName(XMLDefaults.PARTNER_NAME).item(0).getTextContent();
                    String p_id = eElement.getElementsByTagName(XMLDefaults.PARTNER_ID).item(0).getTextContent();
                    String b_name = eElement.getElementsByTagName(XMLDefaults.BOOK_NAME).item(0).getTextContent();
                    String b_id = eElement.getElementsByTagName(XMLDefaults.BOOK_ID).item(0).getTextContent();
                    String d_time = eElement.getElementsByTagName(XMLDefaults.DELIVERY_TIME).item(0).getTextContent();
                    String r_time = eElement.getElementsByTagName(XMLDefaults.RECEIVED_TIME).item(0).getTextContent();

                    result.add(new RentData(p_name, p_id, b_name, b_id, d_time, r_time));
                }
            }
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return result;
    }
}

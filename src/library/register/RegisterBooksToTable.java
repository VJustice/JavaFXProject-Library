package library.register;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.defaults.XMLDefaults;
import library.objects.BookData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class RegisterBooksToTable
{
    public ObservableList<BookData> readXML()
    {
        ObservableList<BookData> result = FXCollections.observableArrayList();
        try
        {
            /** Application **/
            File fXmlFile = new File(XMLDefaults.BOOK_DATA_PATH_XML);

            /** Testing **/
            //File fXmlFile = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(XMLDefaults.BOOK);

            for (int temp = 0; temp < nList.getLength(); temp++)
            {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;

                    String b_name = eElement.getElementsByTagName(XMLDefaults.BOOK_NAME).item(0).getTextContent();
                    String b_id = eElement.getElementsByTagName(XMLDefaults.BOOK_ID).item(0).getTextContent();
                    String b_author = eElement.getElementsByTagName(XMLDefaults.AUTHOR_NAME).item(0).getTextContent();
                    String b_category = eElement.getElementsByTagName(XMLDefaults.BOOK_CATEGORY).item(0).getTextContent();

                    result.add(new BookData(b_name, b_id, b_author, b_category));
                }
            }
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        return result;
    }
}

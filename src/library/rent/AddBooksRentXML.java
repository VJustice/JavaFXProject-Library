package library.rent;

import library.defaults.XMLDefaults;
import library.objects.RentData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class AddBooksRentXML
{
    public void register(RentData rent)
    {
        try
        {
            /** Application **/
            File xml = new File(XMLDefaults.RENT_DATA_PATH_XML);

            /** Testing **/
            //File xml = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);

            Element root = document.getDocumentElement();
            Element new_rent = document.createElement(XMLDefaults.RENT);

            Element partner_name = document.createElement(XMLDefaults.PARTNER_NAME);
            partner_name.appendChild(document.createTextNode(rent.getPartner_name()));
            new_rent.appendChild(partner_name);

            Element partner_id = document.createElement(XMLDefaults.PARTNER_ID);
            partner_id.appendChild(document.createTextNode(rent.getPartner_id()));
            new_rent.appendChild(partner_id);

            Element book_name = document.createElement(XMLDefaults.BOOK_NAME);
            book_name.appendChild(document.createTextNode(rent.getBook_name()));
            new_rent.appendChild(book_name);

            Element book_id = document.createElement(XMLDefaults.BOOK_ID);
            book_id.appendChild(document.createTextNode("" + rent.getBook_id()));
            new_rent.appendChild(book_id);

            Element delivery_time = document.createElement(XMLDefaults.DELIVERY_TIME);
            delivery_time.appendChild(document.createTextNode(rent.getDelivery_time()));
            new_rent.appendChild(delivery_time);

            Element received_time = document.createElement(XMLDefaults.RECEIVED_TIME);
            received_time.appendChild(document.createTextNode(rent.getReceived_time()));
            new_rent.appendChild(received_time);

            root.appendChild(new_rent);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(xml);
            transformer.transform(source, result);

        }catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

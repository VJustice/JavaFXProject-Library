package library.register;


import library.defaults.XMLDefaults;
import library.objects.BookData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class EditBooksXML
{
    public void edition(String selection, BookData rent_data)
    {
        try
        {
            /** Application **/
            File filepath = new File(XMLDefaults.BOOK_DATA_PATH_XML);

            /** Testing **/
            //File filepath = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            NodeList rent = doc.getElementsByTagName(XMLDefaults.BOOK);

            int a = 0;
            for (int i = 0; i < rent.getLength(); i++)
            {
                Node node_rent = rent.item(i);
                NodeList list = node_rent.getChildNodes();

                for(int j = 0; j < list.getLength(); j++)
                {
                    Node node = list.item(j);
                    if (node.getNodeName().equals(XMLDefaults.BOOK_ID) && node.getTextContent().equals(selection))
                    {
                        a = i;
                    }
                }
            }
            Node list_node = rent.item(a);
            NodeList list = list_node.getChildNodes();
            for(int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);

                if(node.getNodeName().equals(XMLDefaults.BOOK_NAME))
                {
                    node.setTextContent(rent_data.getBook_name());
                }
                if(node.getNodeName().equals(XMLDefaults.BOOK_ID))
                {
                    node.setTextContent(rent_data.getBook_id());
                }
                if(node.getNodeName().equals(XMLDefaults.AUTHOR_NAME))
                {
                    node.setTextContent(rent_data.getAuthor_name());
                }
                if(node.getNodeName().equals(XMLDefaults.BOOK_CATEGORY))
                {
                    node.setTextContent(rent_data.getBook_category());
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(filepath);
            transformer.transform(source, result);

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

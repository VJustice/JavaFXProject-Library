package library.rent;

import javafx.collections.ObservableList;
import library.defaults.XMLDefaults;
import library.objects.RentData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DeleteBooksRentXML
{
   public void deletion(ObservableList<RentData> selection)
   {
       try
       {
           /** Application **/
           File filepath = new File(XMLDefaults.RENT_DATA_PATH_XML);

           /** Testing **/
           //File filepath = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xml");

           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           Document doc = factory.newDocumentBuilder().parse(filepath);
           DocumentTraversal traversal = (DocumentTraversal) doc;

           Node a = doc.getDocumentElement();
           NodeIterator iterator = traversal.createNodeIterator(a, NodeFilter.SHOW_ELEMENT, null, true);
           Element b = null;

           for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode())
           {
               Element e = (Element) n;
               if (XMLDefaults.RENT.equals(e.getTagName()))
               {
                   b = e;
               } else if (XMLDefaults.PARTNER_ID.equals(e.getTagName()) &&
                       selection.get(0).getPartner_id().equals(e.getTextContent()) && b != null)
               {
                   a.removeChild(b);
               }
           }

           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           DOMSource source = new DOMSource(doc);
           StreamResult result = new StreamResult(filepath);
           transformer.transform(source, result);

       } catch(Exception e)
       {
           System.err.println(e.getMessage());
       }
   }
}

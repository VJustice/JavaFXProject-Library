package library.register;


import javafx.collections.ObservableList;
import library.defaults.XMLDefaults;
import library.objects.BookData;
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

public class DeleteBooksXML
{
    public void deletion(ObservableList<BookData> selection)
    {
        try
        {
            /** Application **/
            File filepath = new File(XMLDefaults.BOOK_DATA_PATH_XML);

            /** Testing **/
            //File filepath = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(filepath);
            DocumentTraversal traversal = (DocumentTraversal) doc;

            Node a = doc.getDocumentElement();
            NodeIterator iterator = traversal.createNodeIterator(a, NodeFilter.SHOW_ELEMENT, null, true);
            Element b = null;

            for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode())
            {
                Element e = (Element) n;
                if (XMLDefaults.BOOK.equals(e.getTagName()))
                {
                    b = e;
                } else if (XMLDefaults.BOOK_ID.equals(e.getTagName()) &&
                        selection.get(0).getBook_id().equals(e.getTextContent()) && b != null)
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

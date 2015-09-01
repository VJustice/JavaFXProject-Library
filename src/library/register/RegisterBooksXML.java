package library.register;

import library.defaults.XMLDefaults;
import library.objects.BookData;
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

public class RegisterBooksXML
{
    public void register(BookData book)
    {
        try
        {
            /** Application **/
            File xml = new File(XMLDefaults.BOOK_DATA_PATH_XML);

            /** Testing **/
            //File xml = new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);

            Element root = document.getDocumentElement();
            Element new_book = document.createElement(XMLDefaults.BOOK);

            Element book_name = document.createElement(XMLDefaults.BOOK_NAME);
            book_name.appendChild(document.createTextNode(book.getBook_name()));
            new_book.appendChild(book_name);

            Element book_id = document.createElement(XMLDefaults.BOOK_ID);
            book_id.appendChild(document.createTextNode(book.getBook_id()));
            new_book.appendChild(book_id);

            Element author_name = document.createElement(XMLDefaults.AUTHOR_NAME);
            author_name.appendChild(document.createTextNode(book.getAuthor_name()));
            new_book.appendChild(author_name);

            Element book_category = document.createElement(XMLDefaults.BOOK_CATEGORY);
            book_category.appendChild(document.createTextNode(book.getBook_category()));
            new_book.appendChild(book_category);

            root.appendChild(new_book);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(xml);
            transformer.transform(source, result);

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

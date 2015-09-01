package library.admin;

import library.defaults.XMLDefaults;
import library.util.AlertBox;
import org.apache.poi.hssf.usermodel.*;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
@SuppressWarnings("ALL")
public class ExcelToXMLBook
{
    public void generateXML(File excelFile)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement(XMLDefaults.DATA);
            document.appendChild(rootElement);

            InputStream input = new FileInputStream(excelFile);
            HSSFWorkbook workbook = new HSSFWorkbook(input);
            HSSFSheet spreadsheet = workbook.getSheetAt(0);

            for (int i = 1; i <= spreadsheet.getLastRowNum(); i++)
            {
                HSSFRow row_data = spreadsheet.getRow(i);

                Element bookElement = document.createElement(XMLDefaults.BOOK);
                rootElement.appendChild(bookElement);

                Element bookNameElement = document.createElement(XMLDefaults.BOOK_NAME);
                bookElement.appendChild(bookNameElement);
                bookNameElement.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 0).
                                        getStringCellValue()));

                Element bookIDElement = document.createElement(XMLDefaults.BOOK_ID);
                bookElement.appendChild(bookIDElement);
                bookIDElement.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 1).
                                        getStringCellValue()));

                Element authorNameElement = document.createElement(XMLDefaults.AUTHOR_NAME);
                bookElement.appendChild(authorNameElement);
                authorNameElement.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 2).
                                        getStringCellValue()));

                Element bookCategoryElement = document.createElement(XMLDefaults.BOOK_CATEGORY);
                bookElement.appendChild(bookCategoryElement);
                bookCategoryElement.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 3).
                                        getStringCellValue()));
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);

            /** Application **/
            StreamResult result = new StreamResult(new File(XMLDefaults.BOOK_DATA_PATH_XML));

            /** Testing **/
            //StreamResult result = new StreamResult(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml"));
            transformer.transform(source, result);

            new AlertBox().display("Finalizacao", "Accao concluida com sucesso");

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

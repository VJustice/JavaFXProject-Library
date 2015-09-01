package library.admin;


import library.defaults.XMLDefaults;
import library.util.AlertBox;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.FileInputStream;
import java.io.InputStream;

@SuppressWarnings("ALL")
public class ExcelToXMLRent
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

                Element rentElement = document.createElement(XMLDefaults.RENT);
                rootElement.appendChild(rentElement);

                Element rentPartnerName = document.createElement(XMLDefaults.PARTNER_NAME);
                rentElement.appendChild(rentPartnerName);
                rentPartnerName.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 0).
                                        getStringCellValue()));

                Element rentPartnerID = document.createElement(XMLDefaults.PARTNER_ID);
                rentElement.appendChild(rentPartnerID);
                rentPartnerID.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 1).
                                        getStringCellValue()));

                Element rentBookName = document.createElement(XMLDefaults.BOOK_NAME);
                rentElement.appendChild(rentBookName);
                rentBookName.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 2).
                                        getStringCellValue()));

                Element rentBookID = document.createElement(XMLDefaults.BOOK_ID);
                rentElement.appendChild(rentBookID);
                rentBookID.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 3).
                                        getStringCellValue()));

                Element rentDeliveryTime = document.createElement(XMLDefaults.DELIVERY_TIME);
                rentElement.appendChild(rentDeliveryTime);
                rentDeliveryTime.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 4).
                                        getStringCellValue()));

                Element rentReceivedTime = document.createElement(XMLDefaults.RECEIVED_TIME);
                rentElement.appendChild(rentReceivedTime);
                rentReceivedTime.appendChild
                        (document.createTextNode
                                (row_data.getCell((short) 5).
                                        getStringCellValue()));
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);

            /** Application **/
            StreamResult result = new StreamResult(new File(XMLDefaults.RENT_DATA_PATH_XML));

            /** Testing **/
            //StreamResult result = new StreamResult(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xml"));
            transformer.transform(source, result);

            new AlertBox().display("Finalizacao", "Accao concluida com sucesso");

        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

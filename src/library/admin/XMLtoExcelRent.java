package library.admin;


import library.defaults.XMLDefaults;
import library.util.AlertBox;
import org.apache.poi.hssf.usermodel.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;

@SuppressWarnings("ALL")
public class XMLtoExcelRent
{
    public void generateExcel(File xmlDocument)
    {
        try
        {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet spreadSheet = workbook.createSheet("Lista dos Alugueres");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlDocument);

            NodeList nodeList = document.getElementsByTagName(XMLDefaults.RENT);

            HSSFRow row = spreadSheet.createRow(0);

            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("Nome Socio");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 1);
            cell.setCellValue("ID Socio");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 2);
            cell.setCellValue("Nome Livro");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 3);
            cell.setCellValue("ID Livro");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 4);
            cell.setCellValue("Data de Aluguer");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 5);
            cell.setCellValue("Data de Recepcao");
            cell.setCellStyle(cellStyle);

            HSSFRow row_data = null;
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                row_data = spreadSheet.createRow(i + 1);
                cell = row_data.createCell((short) (0));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.PARTNER_NAME).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (1));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.PARTNER_ID).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (2));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.BOOK_NAME).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (3));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.BOOK_ID).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (4));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.DELIVERY_TIME).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (5));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.RECEIVED_TIME).item(0)
                        .getFirstChild().getNodeValue());
            }

            /** Application **/
            FileOutputStream output = new FileOutputStream(new File(XMLDefaults.RENT_DATA_PATH_EXCEL));

            /** Testing **/
            //FileOutputStream output = new FileOutputStream(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xls"));

            workbook.write(output);
            output.flush();
            output.close();

            new AlertBox().display("Finalizacao", "Accao concluida com sucesso");
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}

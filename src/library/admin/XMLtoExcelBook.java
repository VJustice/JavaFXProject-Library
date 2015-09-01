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
public class XMLtoExcelBook
{
    public void generateExcel(File xmlDocument)
    {
        try
        {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet spreadSheet = workbook.createSheet("Lista dos Livros");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlDocument);

            NodeList nodeList = document.getElementsByTagName(XMLDefaults.BOOK);

            HSSFRow row = spreadSheet.createRow(0);

            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);

            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("Nome Livro");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 1);
            cell.setCellValue("ID Livro");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 2);
            cell.setCellValue("Nome Autor");
            cell.setCellStyle(cellStyle);
            cell = row.createCell((short) 3);
            cell.setCellValue("Categoria Livro");
            cell.setCellStyle(cellStyle);

            HSSFRow row_data = null;
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                row_data = spreadSheet.createRow(i + 1);
                cell = row_data.createCell((short) (0));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.BOOK_NAME).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (1));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.BOOK_ID).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (2));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.AUTHOR_NAME).item(0)
                        .getFirstChild().getNodeValue());

                cell = row_data.createCell((short) (3));
                cell.setCellValue(((Element) (nodeList.item(i)))
                        .getElementsByTagName(XMLDefaults.BOOK_CATEGORY).item(0)
                        .getFirstChild().getNodeValue());
            }

            /** Application **/
            FileOutputStream output = new FileOutputStream(new File(XMLDefaults.BOOK_DATA_PATH_EXCEL));

            /** Testing **/
            //FileOutputStream output = new FileOutputStream(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xls"));

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
